package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.File;

/**
 * Removes an empty directory from the file system.
 * 
 * This command deletes a directory that must be empty. It verifies that the
 * specified path exists, is a directory, and contains no files or subdirectories
 * before attempting deletion. The directory path is resolved relative to the
 * current working directory.
 * 
 * Usage: rmdir directory_name
 * 
 * Safety features:
 * - Refuses to delete non-empty directories
 * - Verifies target is actually a directory before deletion
 * - Provides clear error messages for each failure condition
 */
public class RmdirCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        if (args.length == 0) {
            System.out.println("Usage: " + usage());
            return;
        }
        File dir = new File(context.getCurrentDir(), args[0]);
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("Directory not found.");
        } else if (dir.list().length > 0) {
            System.out.println("Directory is not empty.");
        } else if (dir.delete()) {
            System.out.println("Directory deleted.");
        } else {
            System.out.println("Failed to delete directory.");
        }
    }

    @Override
    public String description() {
        return "Remove an empty directory.";
    }

    @Override
    public String usage() {
        return "rmdir <directory_name>";
    }
}
