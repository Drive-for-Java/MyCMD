package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H.mm.ss.SS");
        String formattedTime = now.format(formatter);
        System.out.println("The current time is: " + formattedTime);
    }
}
