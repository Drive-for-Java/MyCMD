package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Displays or changes file attributes.
 *
 * <p>Usage: - attrib [+R | -R] [+A | -A] [+S | -S] [+H | -H] filename
 */
public class AttribCommand implements Command {

    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        if (args.length == 0) {
            System.out.println("Displays or changes file attributes.");
            System.out.println(
                    "\nATTRIB [+R | -R] [+A | -A] [+S | -S] [+H | -H] [[drive:][path]filename] [/S [/D]]");
            System.out.println("\n  +   Sets an attribute");
            System.out.println("  -   Clears an attribute");
            System.out.println("  R   Read-only file attribute");
            System.out.println("  A   Archive file attribute");
            System.out.println("  S   System file attribute");
            System.out.println("  H   Hidden file attribute");
            System.out.println(
                    "  /S  Processes matching files in the current folder and all subfolders");
            System.out.println("  /D  Processes folders as well");
            return;
        }

        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            try {
                StringBuilder cmdBuilder = new StringBuilder("attrib");
                for (String arg : args) {
                    cmdBuilder.append(" \"").append(arg).append("\"");
                }

                ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", cmdBuilder.toString());
                pb.directory(context.getCurrentDir());
                Process process = pb.start();

                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(process.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                process.waitFor();

            } catch (Exception e) {
                System.out.println("Error executing attrib: " + e.getMessage());
            }
        } else {
            // Unix-like system - use ls -l to show permissions
            if (args.length > 0) {
                File file = new File(args[args.length - 1]);
                if (!file.isAbsolute()) {
                    file = new File(context.getCurrentDir(), args[args.length - 1]);
                }

                System.out.println("File: " + file.getName());
                System.out.println("Readable: " + file.canRead());
                System.out.println("Writable: " + file.canWrite());
                System.out.println("Executable: " + file.canExecute());
                System.out.println("Hidden: " + file.isHidden());
            }
        }
    }

    @Override
    public String description() {
        return "Displays or changes file attributes.";
    }

    @Override
    public String usage() {
        return "attrib [+R | -R] [+A | -A] [+S | -S] [+H | -H] filename";
    }
}
