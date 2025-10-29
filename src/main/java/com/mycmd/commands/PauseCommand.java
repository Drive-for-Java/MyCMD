package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

public class PauseCommand implements Command {
    /**
     * Execute the pause command.
     *
     * <p>Prints "Press Enter to continue..." and waits for the user to press Enter before
     * continuing.
     *
     * <p>If an exception occurs during the pause, it is ignored.
     *
     * @param args The arguments to the command.
     * @param context The context of the shell.
     */
    @Override
    public void execute(String[] args, ShellContext context) {
        System.out.println("Press Enter to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
            // Ignore exceptions during pause
        }
    }

    @Override
    public String description() {
        return "Pause execution until user presses Enter.";
    }

    @Override
    public String usage() {
        return "pause";
    }
}
