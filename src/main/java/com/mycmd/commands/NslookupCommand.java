package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Queries DNS to obtain domain name or IP address mapping.
 *
 * <p>Usage: - nslookup domain.com : Query DNS for domain
 */
public class NslookupCommand implements Command {

    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        if (args.length == 0) {
            System.out.println("Usage: nslookup [hostname|IP]");
            System.out.println("\nQueries DNS to obtain domain name or IP address mapping.");
            return;
        }

        try {
            StringBuilder cmdBuilder = new StringBuilder("nslookup");
            for (String arg : args) {
                cmdBuilder.append(" ").append(arg);
            }

            ProcessBuilder pb = new ProcessBuilder();
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("win")) {
                pb.command("cmd.exe", "/c", cmdBuilder.toString());
            } else {
                pb.command("sh", "-c", cmdBuilder.toString());
            }

            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }

            process.waitFor();

        } catch (Exception e) {
            System.out.println("Error executing nslookup: " + e.getMessage());
        }
    }

    @Override
    public String description() {
        return "Queries DNS to obtain domain name or IP address mapping.";
    }

    @Override
    public String usage() {
        return "nslookup [hostname|IP]";
    }
}
