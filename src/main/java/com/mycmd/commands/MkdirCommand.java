package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.File;

public class MkdirCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        if (args.length == 0) {
            System.out.println("Usage: mkdir <directory_name>");
            return;
        }
        File dir = new File(context.getCurrentDir(), args[0]);
        if (dir.exists()) {
            System.out.println("Directory already exists.");
        } else if (dir.mkdirs()) {
            System.out.println("Directory created.");
        } else {
            System.out.println("Failed to create directory.");
        }
    }
}
