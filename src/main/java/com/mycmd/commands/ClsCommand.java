package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

/**
 * Clears the console screen by printing multiple blank lines.
 * 
 * This command simulates clearing the terminal screen by outputting 50 blank
 * lines, pushing previous content out of view. This is a simple cross-platform
 * approach that doesn't rely on terminal-specific control sequences.
 * 
 * Usage: cls
 * 
 * Note: This command does not accept any arguments.
 */
public class ClsCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
    @Override
    public String description() {
        return "Clears the terminal screen.";
    }

    @Override
    public String usage() {
        return "Usage: cls";
    }
}
