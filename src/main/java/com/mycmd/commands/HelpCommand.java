package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.util.Map;

/**
 * Enhanced HelpCommand
 * --------------------
 * Dynamically lists all available commands with their descriptions.
 * 
 * Features:
 * - Automatically updates when new commands are added to the registry.
 * - Displays both command names and short descriptions.
 * - Supports optional usage help: "help <command>".
 */
public class HelpCommand implements Command {
    private final Map<String, Command> commands;

    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void execute(String[] args, ShellContext context) {
        // Case 1: user asked for detailed help about one command
        if (args.length > 1) {
            String cmdName = args[1];
            Command cmd = commands.get(cmdName);

            if (cmd != null) {
                System.out.println("\nCommand: " + cmdName);
                System.out.println("Description: " + cmd.description());
                if (cmd.usage() != null && !cmd.usage().isEmpty()) {
                    System.out.println("Usage: " + cmd.usage());
                } else {
                    System.out.println("Usage: " + cmdName + " [options]");
                }
            } else {
                System.out.println("No such command: " + cmdName);
            }
            return;
        }

        // Case 2: user just typed 'help'
        System.out.println("\nMyCMD — Available Commands:\n");
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            String name = entry.getKey();
            Command cmd = entry.getValue();

            String description = (cmd.description() != null && !cmd.description().isEmpty())
                    ? cmd.description()
                    : "No description available";

            System.out.printf("  %-12s : %s%n", name, description);
        }

        System.out.println("\nType 'help <command>' for detailed info about a specific command.\n");
    }

    @Override
    public String description() {
        return "Show list of available commands and their descriptions.";
    }

    @Override
    public String usage() {
        return "help [command]";
    }
}