package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tests network connectivity to a given hostname or IP address.
 *
 * <p>This command wraps the system ping utility to send ICMP echo requests and displays response
 * times, packet statistics, and timeout status for each attempt. It provides cross-platform support
 * for both Windows and Unix-like systems.
 *
 * <p>Usage:
 * <ul>
 *   <li>ping &lt;hostname&gt; : Ping with default count (4 packets)</li>
 *   <li>ping &lt;hostname&gt; -t : Ping continuously until stopped (Windows/Unix)</li>
 *   <li>ping &lt;hostname&gt; -n &lt;count&gt; : Ping with specified packet count</li>
 *   <li>ping &lt;hostname&gt; -w &lt;timeout&gt; : Set timeout in milliseconds (Windows)</li>
 *   <li>ping &lt;hostname&gt; -l &lt;size&gt; : Set buffer size (Windows)</li>
 * </ul>
 *
 * <p>Examples:
 * <ul>
 *   <li>ping google.com</li>
 *   <li>ping 8.8.8.8</li>
 *   <li>ping google.com -n 10</li>
 *   <li>ping google.com -t</li>
 *   <li>ping 1.1.1.1 -n 5 -w 1000</li>
 * </ul>
 *
 * <p>Statistics displayed:
 * <ul>
 *   <li>Packets: Sent, Received, Lost (with percentage)</li>
 *   <li>Round-trip times: Minimum, Maximum, Average</li>
 * </ul>
 *
 * <p>Note: This implementation uses the system's native ping command for accurate network
 * connectivity testing and proper ICMP handling. Some options may require elevated privileges
 * depending on the operating system.
 */
public class PingCommand implements Command {

    private static final Pattern TIME_PATTERN_WINDOWS =
            Pattern.compile("time[=<]\\s*(\\d+)\\s*ms", Pattern.CASE_INSENSITIVE);
    private static final Pattern TIME_PATTERN_UNIX =
            Pattern.compile("time=(\\d+\\.?\\d*)\\s*ms", Pattern.CASE_INSENSITIVE);

    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        if (args.length == 0) {
            displayHelp();
            return;
        }

        String host = args[0];

        // Validate hostname/IP
        if (!validateHost(host)) {
            System.out.println(
                    "Ping request could not find host "
                            + host
                            + ". Please check the name and try again.");
            return;
        }

        // Resolve hostname to IP
        String resolvedIP = resolveHost(host);
        if (resolvedIP == null) {
            System.out.println(
                    "Ping request could not find host "
                            + host
                            + ". Please check the name and try again.");
            return;
        }

        // Build ping command based on operating system
        String[] command = buildPingCommand(host, args);

        try {
            executePing(command, host, resolvedIP);
        } catch (InterruptedException e) {
            System.out.println("\nPing command was interrupted.");
            Thread.currentThread().interrupt();
        }
    }

    /** Displays usage help for the ping command. */
    private void displayHelp() {
        String os = System.getProperty("os.name").toLowerCase();

        System.out.println("\nUsage: ping [-t] [-n count] [-l size] [-w timeout] target_name\n");
        System.out.println("Options:");
        System.out.println("    -t             Ping the specified host until stopped.");
        System.out.println("                   To stop - press Control-C.");
        System.out.println("    -n count       Number of echo requests to send (default: 4).");

        if (os.contains("win")) {
            System.out.println("    -l size        Send buffer size (default: 32 bytes).");
            System.out.println(
                    "    -w timeout     Timeout in milliseconds to wait for each reply.");
        } else {
            System.out.println(
                    "    -c count       Number of echo requests to send (Unix-style).");
            System.out.println(
                    "    -i interval    Wait interval seconds between sending each packet.");
        }

        System.out.println("\nExamples:");
        System.out.println("    ping google.com");
        System.out.println("    ping 8.8.8.8");
        System.out.println("    ping google.com -n 10");
        System.out.println("    ping 1.1.1.1 -t");
    }

    /** Validates if the host string is a valid hostname or IP address format. */
    private boolean validateHost(String host) {
        if (host == null || host.trim().isEmpty()) {
            return false;
        }

        // Basic validation - allow alphanumeric, dots, hyphens for hostnames
        // More complex validation will happen during resolution
        return host.matches("^[a-zA-Z0-9.-]+$");
    }

    /** Resolves hostname to IP address. */
    private String resolveHost(String host) {
        try {
            InetAddress address = InetAddress.getByName(host);
            return address.getHostAddress();
        } catch (UnknownHostException e) {
            return null;
        }
    }

    /** Executes the ping command and displays results with statistics. */
    private void executePing(String[] command, String host, String resolvedIP)
            throws IOException, InterruptedException {

        String os = System.getProperty("os.name").toLowerCase();

        // Display initial message
        System.out.println("\nPinging " + host + " [" + resolvedIP + "] with 32 bytes of data:");

        ProcessBuilder pb = new ProcessBuilder(command);
        Process process = pb.start();

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader errorReader =
                new BufferedReader(new InputStreamReader(process.getErrorStream()));

        // Statistics tracking
        PingStatistics stats = new PingStatistics();

        String line;
        boolean statsSection = false;

        // Read and display output in real-time
        while ((line = reader.readLine()) != null) {
            System.out.println(line);

            // Parse statistics from output
            if (line.contains("Packets: Sent") || line.contains("packets transmitted")) {
                statsSection = true;
            }

            // Extract time values for custom statistics
            if (!statsSection) {
                extractTimeFromLine(line, stats, os);
            }
        }

        // Display error output if any
        while ((line = errorReader.readLine()) != null) {
            System.err.println(line);
        }

        int exitCode = process.waitFor();

        // If statistics weren't displayed by the native command, show custom stats
        if (!statsSection && stats.received > 0) {
            displayCustomStatistics(stats, resolvedIP);
        }

        if (exitCode != 0 && stats.received == 0) {
            System.out.println("\nPing command failed. Host may be unreachable.");
        }
    }

    /** Extracts time values from ping output lines for statistics. */
    private void extractTimeFromLine(String line, PingStatistics stats, String os) {
        Pattern pattern = os.contains("win") ? TIME_PATTERN_WINDOWS : TIME_PATTERN_UNIX;
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            try {
                double time = Double.parseDouble(matcher.group(1));
                stats.addTime(time);
            } catch (NumberFormatException e) {
                // Ignore parsing errors
            }
        }

        // Count sent/received packets
        if (line.toLowerCase().contains("reply from") || line.toLowerCase().contains("bytes from")) {
            stats.received++;
            stats.sent++;
        } else if (line.toLowerCase().contains("request timed out")
                || line.toLowerCase().contains("destination host unreachable")) {
            stats.sent++;
            stats.lost++;
        }
    }

    /** Displays custom ping statistics when native command doesn't provide them. */
    private void displayCustomStatistics(PingStatistics stats, String ip) {
        System.out.println("\nPing statistics for " + ip + ":");
        System.out.println(
                "    Packets: Sent = "
                        + stats.sent
                        + ", Received = "
                        + stats.received
                        + ", Lost = "
                        + stats.lost
                        + " ("
                        + stats.getLossPercentage()
                        + "% loss),");

        if (stats.received > 0) {
            System.out.println("Approximate round trip times in milli-seconds:");
            System.out.println(
                    "    Minimum = "
                            + String.format("%.0f", stats.minTime)
                            + "ms"
                            + ", Maximum = "
                            + String.format("%.0f", stats.maxTime)
                            + "ms"
                            + ", Average = "
                            + String.format("%.0f", stats.getAverageTime())
                            + "ms");
        }
    }

    /** Builds the appropriate ping command based on the operating system and arguments. */
    private String[] buildPingCommand(String host, String[] args) {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            return buildWindowsPingCommand(host, args);
        } else {
            return buildUnixPingCommand(host, args);
        }
    }

    /** Builds ping command for Windows systems. */
    private String[] buildWindowsPingCommand(String host, String[] args) {
        List<String> command = new ArrayList<>();
        command.add("ping");

        boolean continuous = false;
        Integer count = null;
        Integer timeout = null;
        Integer bufferSize = null;

        // Parse arguments
        for (int i = 1; i < args.length; i++) {
            String arg = args[i].toLowerCase();

            if ("-t".equals(arg)) {
                continuous = true;
            } else if (("-n".equals(arg) || "-c".equals(arg)) && i + 1 < args.length) {
                try {
                    count = Integer.parseInt(args[i + 1]);
                    i++; // Skip next argument
                } catch (NumberFormatException e) {
                    System.out.println(
                            "Warning: Invalid count value '"
                                    + args[i + 1]
                                    + "', using default (4)");
                }
            } else if ("-w".equals(arg) && i + 1 < args.length) {
                try {
                    timeout = Integer.parseInt(args[i + 1]);
                    i++; // Skip next argument
                } catch (NumberFormatException e) {
                    System.out.println(
                            "Warning: Invalid timeout value '" + args[i + 1] + "', using default");
                }
            } else if ("-l".equals(arg) && i + 1 < args.length) {
                try {
                    bufferSize = Integer.parseInt(args[i + 1]);
                    i++; // Skip next argument
                } catch (NumberFormatException e) {
                    System.out.println(
                            "Warning: Invalid buffer size '"
                                    + args[i + 1]
                                    + "', using default (32)");
                }
            }
        }

        // Add options to command
        if (continuous) {
            command.add("-t");
        } else if (count != null && count > 0) {
            command.add("-n");
            command.add(String.valueOf(count));
        } else {
            command.add("-n");
            command.add("4"); // Default count
        }

        if (timeout != null && timeout > 0) {
            command.add("-w");
            command.add(String.valueOf(timeout));
        }

        if (bufferSize != null && bufferSize > 0) {
            command.add("-l");
            command.add(String.valueOf(bufferSize));
        }

        command.add(host);
        return command.toArray(new String[0]);
    }

    /** Builds ping command for Unix-like systems (Linux, macOS). */
    private String[] buildUnixPingCommand(String host, String[] args) {
        List<String> command = new ArrayList<>();
        command.add("ping");

        boolean continuous = false;
        Integer count = null;
        Integer interval = null;

        // Parse arguments
        for (int i = 1; i < args.length; i++) {
            String arg = args[i].toLowerCase();

            if ("-t".equals(arg)) {
                continuous = true;
            } else if (("-n".equals(arg) || "-c".equals(arg)) && i + 1 < args.length) {
                try {
                    count = Integer.parseInt(args[i + 1]);
                    i++; // Skip next argument
                } catch (NumberFormatException e) {
                    System.out.println(
                            "Warning: Invalid count value '"
                                    + args[i + 1]
                                    + "', using default (4)");
                }
            } else if ("-i".equals(arg) && i + 1 < args.length) {
                try {
                    interval = Integer.parseInt(args[i + 1]);
                    i++; // Skip next argument
                } catch (NumberFormatException e) {
                    System.out.println(
                            "Warning: Invalid interval value '" + args[i + 1] + "', using default");
                }
            }
        }

        // Add options to command
        if (!continuous) {
            command.add("-c");
            command.add(count != null && count > 0 ? String.valueOf(count) : "4");
        }

        if (interval != null && interval > 0) {
            command.add("-i");
            command.add(String.valueOf(interval));
        }

        command.add(host);
        return command.toArray(new String[0]);
    }

    @Override
    public String description() {
        return "Tests network connectivity to a hostname or IP address.";
    }

    @Override
    public String usage() {
        return "ping [-t] [-n count] [-w timeout] [-l size] <hostname|IP>";
    }

    /** Helper class to track ping statistics. */
    private static class PingStatistics {
        int sent = 0;
        int received = 0;
        int lost = 0;
        double minTime = Double.MAX_VALUE;
        double maxTime = 0;
        double totalTime = 0;

        void addTime(double time) {
            if (time < minTime) minTime = time;
            if (time > maxTime) maxTime = time;
            totalTime += time;
        }

        double getAverageTime() {
            return received > 0 ? totalTime / received : 0;
        }

        int getLossPercentage() {
            return sent > 0 ? (lost * 100) / sent : 0;
        }
    }
}
