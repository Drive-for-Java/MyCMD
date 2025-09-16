package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.File;

public class RmdirCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        if (args.length == 0) {
            System.out.println("Usage: rmdir <directory_name>");
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
}
