package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.util.Map;

/**
 * Displays a list of all available commands in the shell.
 * 
 * This command provides users with an overview of commands registered in
 * the shell. It requires access to the command registry (Map) which is
 * provided during construction. Each command name is printed on a separate
 * line with a bullet point prefix.
 * 
 * Usage: help
 * 
 * The command iterates through all keys in the command registry and displays
 * them in the order provided by the map's key set.
 */
public class HelpCommand implements Command {
    private final Map<String, Command> commands;

    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void execute(String[] args, ShellContext context) {
        System.out.println("Available commands:");
        for (String key : commands.keySet()) {
            System.out.println(" - " + key);
        }
    }
}
