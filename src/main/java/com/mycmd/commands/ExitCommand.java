package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

/**
 * Terminates the MyCMD shell application.
 * 
 * This command exits the shell by calling System.exit(0), which immediately
 * terminates the JVM process with a success status code. A goodbye message
 * is displayed before exiting.
 * 
 * Usage: exit
 * 
 * Note: This command does not accept any arguments and exits immediately
 * without prompting for confirmation.
 */
public class ExitCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        System.out.println("Exiting MyCMD...");
        System.exit(0);
    }

    @Override
    public String description() {
        return "Exit the program.";
    }

    @Override
    public String usage() {
        return "exit";
    }
}
