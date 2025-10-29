package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.IOException;

/**
 * Network command - manages network resources and connections.
 *
 * <p>Usage: - net user : Display user accounts - net use : Connect/disconnect network drives - net
 * start : Start services
 */
public class NetCommand implements Command {

    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        if (args.length == 0) {
            System.out.println("The syntax of this command is:");
            System.out.println("\nNET");
            System.out.println(
                    "    [ ACCOUNTS | COMPUTER | CONFIG | CONTINUE | FILE | GROUP | HELP |");
            System.out.println("      HELPMSG | LOCALGROUP | PAUSE | SESSION | SHARE | START |");
            System.out.println("      STATISTICS | STOP | TIME | USE | USER | VIEW ]");
            System.out.println("\nCommon commands:");
            System.out.println("  NET USER                  - Displays user account information");
            System.out.println(
                    "  NET USE                   - Connects/disconnects network resources");
            System.out.println("  NET START                 - Lists or starts network services");
            System.out.println("  NET STOP                  - Stops network services");
            System.out.println("  NET SHARE                 - Displays shared resources");
            System.out.println("  NET SESSION               - Lists or disconnects sessions");
            System.out.println("\nNote: Most NET commands require administrator privileges.");
            return;
        }

        String subCommand = args[0].toLowerCase();

        try {
            StringBuilder cmdBuilder = new StringBuilder("net ").append(subCommand);
            for (int i = 1; i < args.length; i++) {
                cmdBuilder.append(" ").append(args[i]);
            }

            ProcessBuilder pb = new ProcessBuilder();
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("win")) {
                pb.command("cmd.exe", "/c", cmdBuilder.toString());
            } else {
                System.out.println("NET command is primarily for Windows systems.");
                return;
            }

            Process process = pb.start();
            java.io.BufferedReader reader =
                    new java.io.BufferedReader(
                            new java.io.InputStreamReader(process.getInputStream()));
            java.io.BufferedReader errorReader =
                    new java.io.BufferedReader(
                            new java.io.InputStreamReader(process.getErrorStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }

            process.waitFor();

        } catch (Exception e) {
            System.out.println("Error executing net command: " + e.getMessage());
        }
    }

    @Override
    public String description() {
        return "Network command - manages network resources and connections.";
    }

    @Override
    public String usage() {
        return "net [user | use | start | stop | share | session] [options]";
    }
}
