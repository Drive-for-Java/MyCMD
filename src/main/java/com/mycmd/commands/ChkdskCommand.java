package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Checks a disk and displays a status report.
 *
 * <p>Usage: - chkdsk [drive:] : Check specified drive - chkdsk /F : Fixes errors on the disk
 */
public class ChkdskCommand implements Command {

    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        String os = System.getProperty("os.name").toLowerCase();

        if (!os.contains("win")) {
            System.out.println("CHKDSK is only available on Windows systems.");
            System.out.println("On Unix-like systems, use 'fsck' instead.");
            return;
        }

        if (args.length == 0) {
            System.out.println("Checks a disk and displays a status report.");
            System.out.println("\nCHKDSK [volume[[path]filename]]] [/F] [/V] [/R] [/X]");
            System.out.println(
                    "\n  volume          Specifies the drive letter (followed by a colon)");
            System.out.println("  /F              Fixes errors on the disk");
            System.out.println("  /V              Displays the full path of every file");
            System.out.println(
                    "  /R              Locates bad sectors and recovers readable information");
            System.out.println(
                    "  /X              Forces the volume to dismount first if necessary");
            System.out.println("\nNote: Administrator privileges required.");
            return;
        }

        try {
            StringBuilder cmdBuilder = new StringBuilder("chkdsk");
            for (String arg : args) {
                cmdBuilder.append(" ").append(arg);
            }

            Process process = Runtime.getRuntime().exec(cmdBuilder.toString());
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
            System.out.println("Error executing chkdsk: " + e.getMessage());
            System.out.println("Administrator privileges may be required.");
        }
    }

    @Override
    public String description() {
        return "Checks a disk and displays a status report.";
    }

    @Override
    public String usage() {
        return "chkdsk [volume] [/F] [/V] [/R] [/X]";
    }
}
