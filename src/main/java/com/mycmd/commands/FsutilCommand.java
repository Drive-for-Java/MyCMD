package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * File System Utility - Performs file system related operations.
 *
 * <p>Usage: - fsutil fsinfo drives : List all drives - fsutil volume list : List all volumes
 */
public class FsutilCommand implements Command {

    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        String os = System.getProperty("os.name").toLowerCase();

        if (!os.contains("win")) {
            System.out.println("FSUTIL is only available on Windows systems.");
            return;
        }

        if (args.length == 0) {
            System.out.println("---- FSUTIL Commands Supported ----");
            System.out.println("\nfsinfo      File System Information");
            System.out.println("volume      Volume management");
            System.out.println("file        File specific commands");
            System.out.println("hardlink    Hardlink management");
            System.out.println("usn         USN management");
            System.out.println("\nNote: Administrator privileges required for most operations.");
            return;
        }

        try {
            StringBuilder cmdBuilder = new StringBuilder("fsutil");
            for (String arg : args) {
                cmdBuilder.append(" ").append(arg);
            }

            Process process = Runtime.getRuntime().exec(cmdBuilder.toString());
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
            System.out.println("Error executing fsutil: " + e.getMessage());
        }
    }

    @Override
    public String description() {
        return "File System Utility - Performs file system operations.";
    }

    @Override
    public String usage() {
        return "fsutil [command] [options]";
    }
}
