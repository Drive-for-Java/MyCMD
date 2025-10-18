package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.time.LocalDate;

/**
 * Command that prints the current date to standard output.
 *
 * <p>This command does not use the provided {@link ShellContext} but keeps the
 * parameter to conform to the {@link Command} contract. The output is produced
 * using {@link LocalDate#now()} and printed in ISO-8601 format (yyyy-MM-dd).</p>
 */
public class DateCommand implements Command {
    /**
     * Print the current date.
     *
     * @param args    ignored for this command; may be empty or contain unused tokens.
     * @param context the current shell context; not used by this command.
     */
    @Override
    public void execute(String[] args, ShellContext context) {
        System.out.println("The current date is: " + java.time.LocalDate.now());
    }
    @Override
    public String description() {
        return "Displays the current system date.";
    }

    @Override
    public String usage() {
        return "Usage: date";
    }
}
