package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.File;

/**
 * Deletes one or more files from the file system.
 * 
 * This command accepts one or more file names as arguments and attempts to
 * delete each file. File paths are resolved relative to the current working
 * directory. The command provides feedback for each file indicating whether
 * the deletion was successful or if the file was not found.
 * 
 * Usage: del file1 [file2 file3 ...]
 * 
 * Note: This command only deletes files, not directories. Use rmdir for
 * directory removal.
 */
public class DelCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        if (args.length == 0) {
            System.out.println("Usage: del <file_name>");
            return;
        }
        for (String name : args) {
            File file = new File(context.getCurrentDir(), name);
            if (!file.exists() || !file.isFile()) {
                System.out.println("File not found: " + name);
            } else if (file.delete()) {
                System.out.println("Deleted: " + name);
            } else {
                System.out.println("Failed to delete: " + name);
            }
        }
    }
    @Override
    public String description() {
        return "Deletes one or more files.";
    }

    @Override
    public String usage() {
        return "Usage: del <filename>";
    }
}
