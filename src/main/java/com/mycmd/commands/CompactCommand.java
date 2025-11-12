package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Displays or alters the compression of files on NTFS partitions.
 *
 * <p>Usage: - compact [filename] : Display compression state - compact /C [filename] : Compress
 * files
 */
public class CompactCommand implements Command {

    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        String os = System.getProperty("os.name").toLowerCase();

        if (!os.contains("win")) {
            System.out.println("COMPACT is only available on Windows NTFS systems.");
            return;
        }

        if (args.length == 0) {
            System.out.println("Displays or alters the compression of files on NTFS partitions.");
            System.out.println("\nCOMPACT [/C | /U] [/S[:dir]] [/A] [/I] [/F] [/Q] [filename [...]]");
            System.out.println("\n  /C        Compresses the specified files.");
            System.out.println("  /U        Uncompresses the specified files.");
            System.out.println("  /S        Performs the specified operation on files in the given");
            System.out.println("            directory and all subdirectories.");
            System.out.println("  /A        Displays files with the hidden or system attributes.");
            System.out.println("  /I        Continues performing the specified operation even after");
            System.out.println("            errors have occurred.");
            System.out.println("  /F        Forces the compress operation on all specified files.");
            System.out.println("  /Q        Reports only the most essential information.");
            return;
        }

        try {
            StringBuilder cmdBuilder = new StringBuilder("compact");
            for (String arg : args) {
                cmdBuilder.append(" \"").append(arg).append("\"");
            }

            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", cmdBuilder.toString());
            pb.directory(context.getCurrentDir());
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            process.waitFor();

        } catch (Exception e) {
            System.out.println("Error executing compact: " + e.getMessage());
        }
    }

    @Override
    public String description() {
        return "Displays or alters the compression of files on NTFS partitions.";
    }

    @Override
    public String usage() {
        return "compact [/C | /U] [filename]";
    }
}
