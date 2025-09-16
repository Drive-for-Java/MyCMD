package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

public class ExitCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        System.out.println("Exiting MyCMD...");
        System.exit(0);
    }
}
