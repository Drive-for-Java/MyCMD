package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.IOException;

/**
 * Sends a message to a user.
 *
 * <p>Usage: - msg username message : Send message to user
 */
public class MsgCommand implements Command {

    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        if (args.length < 2) {
            System.out.println("Send a message to a user.");
            System.out.println("\nMSG {username | sessionname | sessionid | @filename | *}");
            System.out.println("    [/SERVER:servername] [/TIME:seconds] [/V] [/W] [message]");
            System.out.println("\n  username            Identifies the specified username.");
            System.out.println("  sessionname         The name of the session.");
            System.out.println("  sessionid           The ID of the session.");
            System.out.println("  @filename           Identifies a file containing a list of usernames.");
            System.out.println("  *                   Sends message to all sessions on specified server.");
            System.out.println("  /SERVER:servername  Server to contact (default is current).");
            System.out.println("  /TIME:seconds       Time to wait for receiver to acknowledge msg.");
            System.out.println("  /V                  Display information about actions being performed.");
            System.out.println("  /W                  Wait for response from user, useful with /V.");
            System.out.println("  message             Message to send.");
            return;
        }

        String os = System.getProperty("os.name").toLowerCase();

        if (!os.contains("win")) {
            System.out.println("MSG command is primarily for Windows systems.");
            System.out.println("On Unix-like systems, use 'write' or 'wall' instead.");
            return;
        }

        try {
            StringBuilder cmdBuilder = new StringBuilder("msg");
            for (String arg : args) {
                if (arg.contains(" ")) {
                    cmdBuilder.append(" \"").append(arg).append("\"");
                } else {
                    cmdBuilder.append(" ").append(arg);
                }
            }

            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", cmdBuilder.toString());
            Process process = pb.start();

            java.io.BufferedReader reader =
                    new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()));
            java.io.BufferedReader errorReader =
                    new java.io.BufferedReader(new java.io.InputStreamReader(process.getErrorStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }

            process.waitFor();

        } catch (Exception e) {
            System.out.println("Error executing msg: " + e.getMessage());
        }
    }

    @Override
    public String description() {
        return "Sends a message to a user.";
    }

    @Override
    public String usage() {
        return "msg username [message]";
    }
}
