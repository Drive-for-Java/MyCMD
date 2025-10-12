package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.time.LocalDate;

public class DateCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        System.out.println("The current date is: " + java.time.LocalDate.now());
    }
}
