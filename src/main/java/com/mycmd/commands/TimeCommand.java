package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Command that prints the current local time to standard output using a simple
 * custom formatter.
 *
 * <p>The time is formatted with the pattern "H.mm.ss.SS" where:
 * <ul>
 *   <li>H - hour-of-day (0-23)</li>
 *   <li>mm - minute-of-hour</li>
 *   <li>ss - second-of-minute</li>
 *   <li>SS - fraction-of-second (two digits)</li>
 * </ul>
 * Example output: "The current time is: 13.5.07.12"</p>
 */
public class TimeCommand implements Command {
    /**
     * Print the current time.
     *
     * @param args    ignored for this command; may be empty or contain unused tokens.
     * @param context the current shell context; not used by this command.
     */
    @Override
    public void execute(String[] args, ShellContext context) {
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H.mm.ss.SS");
        String formattedTime = now.format(formatter);
        System.out.println("The current time is: " + formattedTime);
    }
}
