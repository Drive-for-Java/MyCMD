package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.File;

/**
 * Changes the current working directory or displays the current directory path.
 * 
 * This command allows navigation through the file system hierarchy. When called
 * without arguments, it prints the absolute path of the current directory. When
 * provided with a path argument, it attempts to change to that directory.
 * 
 * Usage:
 * - cd          : Display current directory
 * - cd path     : Change to specified path (absolute or relative)
 * - cd ..       : Navigate to parent directory
 * 
 * The command handles both absolute and relative paths. Relative paths are
 * resolved against the current working directory stored in ShellContext.
 */
public class CdCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        // If no argument, print current directory
        if (args.length == 0) {
            System.out.println(context.getCurrentDir().getAbsolutePath());
            return;
        }

        String dir = args[0];
        File newDir;

        // Handle "cd .." (go to parent directory)
        if (dir.equals("..")) {
            File parent = context.getCurrentDir().getParentFile();
            if (parent == null) {
                System.out.println("Already at the root directory.");
                return;
            }
            newDir = parent;
        } else {
            newDir = new File(dir);
            if (!newDir.isAbsolute()) {
                newDir = new File(context.getCurrentDir(), dir);
            }
        }

        // Change directory if valid
        if (newDir.exists() && newDir.isDirectory()) {
            context.setCurrentDir(newDir);
        } else {
            System.out.println("The system cannot find the path specified.");
        }
    }

    @Override
    public String description() {
        return "Change the current working directory or display it.";
    }

    @Override
    public String usage() {
        return "cd [path]";
    }
}
