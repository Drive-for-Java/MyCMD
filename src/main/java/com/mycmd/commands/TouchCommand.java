package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.File;
import java.io.IOException;

/**
 * Creates a new empty file or updates the timestamp of an existing file.
 * 
 * This command mimics the Unix touch command behavior. If the specified file
 * does not exist, it creates a new empty file. If the file already exists,
 * it updates the last modified timestamp to the current time without changing
 * the file contents.
 * 
 * Usage: touch filename
 * 
 * The file is created or updated in the current working directory. The command
 * provides feedback indicating whether a new file was created or an existing
 * file's timestamp was updated.
 */
public class TouchCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        if (args.length < 1) {  // ✅ Check for at least 1 argument
            System.out.println("Usage: touch <filename>");
            return;
        }
        
        File file = new File(context.getCurrentDir(), args[0]);  // ✅ Use args[0]
        if (file.createNewFile()) {
            System.out.println("File created: " + args[0]);  // ✅ Use args[0]
        } else {
            // Update timestamp
            file.setLastModified(System.currentTimeMillis());
            System.out.println("File timestamp updated: " + args[0]);  // ✅ Use args[0]
        }
    }
}