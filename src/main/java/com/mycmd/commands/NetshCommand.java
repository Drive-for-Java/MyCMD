package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Network Shell - command-line scripting utility for network components.
 *
 * <p>Usage: - netsh interface show : Show network interfaces - netsh wlan show : Show WLAN
 * information
 */
public class NetshCommand implements Command {

    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        String os = System.getProperty("os.name").toLowerCase();

        if (!os.contains("win")) {
            System.out.println("NETSH is only available on Windows systems.");
            return;
        }

        if (args.length == 0) {
            System.out.println("Network Shell - command-line scripting utility.");
            System.out.println("\nCommon commands:");
            System.out.println("  netsh interface show interface");
            System.out.println("  netsh interface ip show config");
            System.out.println("  netsh wlan show profiles");
            System.out.println("  netsh wlan show networks");
            System.out.println("  netsh firewall show state");
            System.out.println("\nNote: Some commands require administrator privileges.");
            return;
        }

        try {
            StringBuilder cmdBuilder = new StringBuilder("netsh");
            for (String arg : args) {
                cmdBuilder.append(" ").append(arg);
            }

            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", cmdBuilder.toString());
            Process process = pb.start();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader =
                    new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }

            process.waitFor();

        } catch (Exception e) {
            System.out.println("Error executing netsh: " + e.getMessage());
        }
    }

    @Override
    public String description() {
        return "Network Shell - command-line scripting utility for network components.";
    }

    @Override
    public String usage() {
        return "netsh [context] [command]";
    }
}
