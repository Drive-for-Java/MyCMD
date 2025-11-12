package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Extended copy - Copies files and directory trees.
 *
 * <p>Usage: - xcopy source [destination] [/S] [/E] [/Y]
 */
public class XcopyCommand implements Command {

    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        if (args.length == 0) {
            System.out.println("Copies files and directory trees.");
            System.out.println("\nXCOPY source [destination] [/A | /M] [/D[:date]] [/P] [/S [/E]]");
            System.out.println("                          [/V] [/W] [/C] [/I] [/Q] [/F] [/L]");
            System.out.println("                          [/H] [/R] [/T] [/U] [/K] [/N] [/O]");
            System.out.println("                          [/X] [/Y] [/-Y] [/Z] [/B]");
            System.out.println("\n  /S           Copies directories and subdirectories except empty ones.");
            System.out.println("  /E           Copies directories and subdirectories, including empty ones.");
            System.out.println("  /Y           Suppresses prompting to confirm overwriting.");
            System.out.println("  /I           If destination does not exist and copying more than one file,");
            System.out.println("               assumes that destination must be a directory.");
            return;
        }

        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            try {
                StringBuilder cmdBuilder = new StringBuilder("xcopy");
                for (String arg : args) {
                    cmdBuilder.append(" \"").append(arg).append("\"");
                }

                ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", cmdBuilder.toString());
                pb.directory(context.getCurrentDir());
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
                System.out.println("Error executing xcopy: " + e.getMessage());
            }
        } else {
            System.out.println("XCOPY is not available on this system. Use 'cp -r' instead.");
        }
    }

    @Override
    public String description() {
        return "Copies files and directory trees.";
    }

    @Override
    public String usage() {
        return "xcopy source [destination] [/S] [/E] [/Y]";
    }
}
