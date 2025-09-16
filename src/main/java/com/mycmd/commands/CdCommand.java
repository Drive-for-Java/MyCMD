package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.File;

public class CdCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        if (args.length == 0) {
            System.out.println(context.getCurrentDir().getAbsolutePath());
            return;
        }
        File newDir = new File(args[0]);
        if (!newDir.isAbsolute()) {
            newDir = new File(context.getCurrentDir(), args[0]);
        }
        if (newDir.exists() && newDir.isDirectory()) {
            context.setCurrentDir(newDir);
        } else {
            System.out.println("The system cannot find the path specified.");
        }
    }
}
