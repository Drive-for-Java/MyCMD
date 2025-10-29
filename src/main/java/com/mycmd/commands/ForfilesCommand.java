package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Selects files in a directory and runs a command on each file.
 *
 * <p>Usage: - forfiles /S /M *.txt /C "cmd /c echo @file"
 */
public class ForfilesCommand implements Command {

    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        System.out.println("FORFILES [/P pathname] [/M searchmask] [/S]");
        System.out.println("         [/C command] [/D [+ | -] {MM/dd/yyyy | dd}]");
        System.out.println("\nDescription:");
        System.out.println("    Selects a file (or set of files) and executes a");
        System.out.println("    command on that file. This is helpful for batch jobs.");
        System.out.println("\nParameter List:");
        System.out.println("    /P    pathname      Indicates the path to start searching.");
        System.out.println("    /M    searchmask    Searches files according to a searchmask.");
        System.out.println("    /S                  Recurses into subdirectories.");
        System.out.println(
                "    /C    command       Indicates the command to execute for each file.");
        System.out.println(
                "    /D    date          Selects files with a last modified date greater");
        System.out.println(
                "                        than or equal to (+), or less than or equal to");
        System.out.println("                        (-), the specified date.");

        if (args.length == 0) {
            return;
        }

        String os = System.getProperty("os.name").toLowerCase();

        if (!os.contains("win")) {
            System.out.println("\nFORFILES is only available on Windows systems.");
            System.out.println("On Unix-like systems, use 'find' with '-exec' instead.");
            return;
        }

        try {
            StringBuilder cmdBuilder = new StringBuilder("forfiles");
            for (String arg : args) {
                cmdBuilder.append(" ").append(arg);
            }

            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", cmdBuilder.toString());
            pb.directory(context.getCurrentDir());
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
            System.out.println("Error executing forfiles: " + e.getMessage());
        }
    }

    @Override
    public String description() {
        return "Selects files in a directory and runs a command on each file.";
    }

    @Override
    public String usage() {
        return "forfiles [/P pathname] [/M searchmask] [/S] [/C command]";
    }
}
