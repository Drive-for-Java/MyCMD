package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

public class TitleCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        if (args.length > 0) {
            String title = String.join(" ", args);
            System.out.println("\033]0;" + title + "\007");
        }
    }
    
}
