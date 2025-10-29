package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.IOException;

/**
 * Starts a separate window to run a specified program or command.
 *
 * <p>Usage: - start program.exe : Start a program - start "title" cmd : Start with window title
 */
public class StartCommand implements Command {

    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        if (args.length == 0) {
            System.out.println("Starts a separate window to run a specified program or command.");
            System.out.println("\nSTART [\"title\"] [/D path] [/I] [/MIN] [/MAX] [/WAIT]");
            System.out.println("      [/B] [command/program] [parameters]");
            System.out.println("\n    \"title\"     Title to display in window title bar.");
            System.out.println("    /D path     Specifies startup directory.");
            System.out.println("    /MIN        Start window minimized.");
            System.out.println("    /MAX        Start window maximized.");
            System.out.println("    /WAIT       Start application and wait for it to terminate.");
            System.out.println("    /B          Start application without creating a new window.");
            return;
        }

        String os = System.getProperty("os.name").toLowerCase();

        try {
            if (os.contains("win")) {
                // Windows - use start command
                StringBuilder cmdBuilder = new StringBuilder("start");
                for (String arg : args) {
                    if (arg.contains(" ")) {
                        cmdBuilder.append(" \"").append(arg).append("\"");
                    } else {
                        cmdBuilder.append(" ").append(arg);
                    }
                }

                ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", cmdBuilder.toString());
                pb.directory(context.getCurrentDir());
                pb.start(); // Don't wait for completion

                System.out.println("Started: " + String.join(" ", args));

            } else {
                // Unix-like systems - use nohup or direct execution
                ProcessBuilder pb = new ProcessBuilder(args);
                pb.directory(context.getCurrentDir());
                pb.start();

                System.out.println("Started: " + String.join(" ", args));
            }

        } catch (Exception e) {
            System.out.println("Error starting process: " + e.getMessage());
        }
    }

    @Override
    public String description() {
        return "Starts a separate window to run a specified program or command.";
    }

    @Override
    public String usage() {
        return "start [\"title\"] [/D path] [/MIN] [/MAX] [/WAIT] [command]";
    }
}
