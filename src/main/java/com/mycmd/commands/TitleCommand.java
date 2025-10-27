package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

/**
 * Sets the terminal window title using ANSI escape sequences.
 * 
 * This command changes the title bar text of the terminal window by emitting
 * an ANSI Operating System Command (OSC) escape sequence. The title is set to
 * the concatenation of all provided arguments, joined with spaces.
 * 
 * Usage: title new window title
 * 
 * Note: This command works in terminals that support ANSI OSC sequences.
 * Some terminals may ignore or handle this escape sequence differently.
 * If no arguments are provided, usage information is displayed.
 */
public class TitleCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        if (args.length > 0) {
            String title = String.join(" ", args);
            System.out.println("\033]0;" + title + "\007");
        } else {
            System.out.println("Usage: " + usage());
        }
    }
    
    @Override
    public String description() {
        return "Set the terminal window title.";
    }

    @Override
    public String usage() {
        return "title <new title>";
    }
}
