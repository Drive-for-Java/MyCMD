package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Displays MAC addresses for all network adapters.
 *
 * <p>Usage: - getmac : Display MAC addresses - getmac /V : Verbose output
 */
public class GetmacCommand implements Command {

    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            try {
                StringBuilder cmdBuilder = new StringBuilder("getmac");
                for (String arg : args) {
                    cmdBuilder.append(" ").append(arg);
                }

                ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", cmdBuilder.toString());
                Process process = pb.start();

                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(process.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                process.waitFor();

            } catch (Exception e) {
                System.out.println("Error executing getmac: " + e.getMessage());
            }
        } else {
            // Cross-platform implementation
            System.out.println("Network Adapter MAC Addresses:\n");
            try {
                Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
                while (networks.hasMoreElements()) {
                    NetworkInterface network = networks.nextElement();
                    byte[] mac = network.getHardwareAddress();

                    if (mac != null) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < mac.length; i++) {
                            sb.append(
                                    String.format(
                                            "%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                        }
                        System.out.printf("%-20s %s%n", network.getName(), sb.toString());
                    }
                }
            } catch (Exception e) {
                System.out.println("Error retrieving MAC addresses: " + e.getMessage());
            }
        }
    }

    @Override
    public String description() {
        return "Displays MAC addresses for all network adapters.";
    }

    @Override
    public String usage() {
        return "getmac [/V]";
    }
}
