package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

/**
 * Displays text messages to the console output.
 *
 * <p>This command prints all provided arguments to standard output, joining multiple arguments with
 * spaces. When called without arguments, it prints a blank line.
 *
 * <p>Usage: - echo : Print a blank line - echo message : Print the message to console
 *
 * <p>Multiple words are automatically joined with spaces between them.
 */
public class EchoCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        if (args.length == 0) {
            System.out.println();
        } else {
            System.out.println(String.join(" ", args));
        }
    }

    @Override
    public String description() {
        return "Display a line of text";
    }

    @Override
    public String usage() {
        return "echo <text>";
    }
}
