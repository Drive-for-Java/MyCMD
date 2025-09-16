package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.File;

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
}
