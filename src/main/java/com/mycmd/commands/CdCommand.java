package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.File;

public class CdCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        // No argument -> print current directory
        if (args.length == 0) {
            System.out.println(context.getCurrentDir().getAbsolutePath());
            return;
        }

        String dir = args[0];

        // Normalize "cd.." without space to ".."
        if (dir.equals("cd..")) {
            dir = "..";
        }

        File newDir = new File(dir);

        // If relative path, resolve from current directory
        if (!newDir.isAbsolute()) {
            newDir = new File(context.getCurrentDir(), dir);
        }

        // Handle "cd .." when already at root
        if (dir.equals("..")) {
            File parent = context.getCurrentDir().getParentFile();
            if (parent == null) {
                System.out.println("Already at the root directory.");
                return;
            }
            newDir = parent;
        }

        if (newDir.exists() && newDir.isDirectory()) {
            context.setCurrentDir(newDir);
        } else {
            System.out.println("The system cannot find the path specified.");
        }
    }
}
