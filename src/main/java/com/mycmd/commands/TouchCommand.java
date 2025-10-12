package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.File;
import java.io.IOException;

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