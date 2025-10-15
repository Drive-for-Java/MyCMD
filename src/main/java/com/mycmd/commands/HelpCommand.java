package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.util.Map;

/**
 * Enhanced HelpCommand
 * --------------------
 * Dynamically lists all available commands with their descriptions.
 * Supports detailed help for individual commands using 'help <command>'.
 */
public class HelpCommand implements Command {
    private final Map<String, Command> commands;

    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void execute(String[] args, ShellContext context) {
        // Detailed help for a specific command
        if (args.length > 1) {
            String cmdName = args[1];
            Command cmd = commands.get(cmdName);

            if (cmd != null) {
                System.out.println("\nCommand: " + cmdName);
                System.out.println("Description: " + cmd.description());
                System.out.println("Usage: " + (cmd.usage() != null ? cmd.usage() : cmdName + " [options]"));
            } else {
                System.out.println("No such command: " + cmdName);
            }
            return;
        }

        // General help listing all commands
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