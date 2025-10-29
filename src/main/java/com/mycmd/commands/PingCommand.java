package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Tests network connectivity to a given hostname or IP address.
 *
 * <p>This command wraps the system ping utility to send ICMP echo requests and displays response
 * times or timeout status for each attempt. It uses the native ping command available on the
 * operating system.
 *
 * <p>Usage: - ping <hostname> : Ping the specified host with default count (4 packets) - ping
 * <hostname> -t : Ping continuously until stopped (Windows-style) - ping <hostname> -n <count> :
 * Ping with specified packet count (Windows-style)
 *
 * <p>Examples: - ping google.com - ping 8.8.8.8 - ping google.com -n 10
 *
 * <p>Note: This implementation uses the system's native ping command for accurate network
 * connectivity testing and proper ICMP handling.
 */
public class PingCommand implements Command {

    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        if (args.length == 0) {
            System.out.println("Usage: ping <hostname> [options]");
            System.out.println("Options:");
            System.out.println("  -t          Ping continuously until stopped");
            System.out.println("  -n <count>  Number of echo requests to send");
            System.out.println();
            System.out.println("Examples:");
            System.out.println("  ping google.com");
            System.out.println("  ping 8.8.8.8");
            System.out.println("  ping google.com -n 10");
            return;
        }

        String host = args[0];

        // Build ping command based on operating system
        String[] command = buildPingCommand(host, args);

        try {
            // Execute the ping command
            ProcessBuilder pb = new ProcessBuilder(command);
            Process process = pb.start();

            // Read and display output in real-time
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader =
                    new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String line;
            // Display standard output
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Display error output if any
            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }

            // Wait for the process to complete
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                System.out.println("Ping command failed with exit code: " + exitCode);
            }

        } catch (InterruptedException e) {
            System.out.println("Ping command was interrupted.");
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            throw new IOException("Failed to execute ping command: " + e.getMessage(), e);
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
        java.util.List<String> command = new java.util.ArrayList<>();
        command.add("ping");

        // Parse arguments for Windows ping options
        boolean continuous = false;
        int count = 4; // Default count

        for (int i = 1; i < args.length; i++) {
            if ("-t".equals(args[i])) {
                continuous = true;
            } else if ("-n".equals(args[i]) && i + 1 < args.length) {
                try {
                    count = Integer.parseInt(args[i + 1]);
                    i++; // Skip the next argument as it's the count value
                } catch (NumberFormatException e) {
                    System.out.println("Warning: Invalid count value, using default (4)");
                }
            }
        }

        if (continuous) {
            command.add("-t");
        } else {
            command.add("-n");
            command.add(String.valueOf(count));
        }

        command.add(host);
        return command.toArray(new String[0]);
    }

    /** Builds ping command for Unix-like systems (Linux, macOS). */
    private String[] buildUnixPingCommand(String host, String[] args) {
        java.util.List<String> command = new java.util.ArrayList<>();
        command.add("ping");

        // Parse arguments for Unix ping options
        int count = 4; // Default count

        for (int i = 1; i < args.length; i++) {
            if ("-t".equals(args[i])) {
                // Unix ping doesn't use -t, just omit count for continuous
                count = -1;
            } else if ("-n".equals(args[i]) && i + 1 < args.length) {
                try {
                    count = Integer.parseInt(args[i + 1]);
                    i++; // Skip the next argument as it's the count value
                } catch (NumberFormatException e) {
                    System.out.println("Warning: Invalid count value, using default (4)");
                }
            }
        }

        if (count > 0) {
            command.add("-c");
            command.add(String.valueOf(count));
        }

        command.add(host);
        return command.toArray(new String[0]);
    }

    @Override
    public String description() {
        return "Tests network connectivity to a hostname or IP address";
    }

    @Override
    public String usage() {
        return "ping <hostname> [options]";
    }
}
