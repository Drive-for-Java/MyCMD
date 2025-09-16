package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.File;

public class DirCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        File[] files = context.getCurrentDir().listFiles();
        if (files == null || files.length == 0) {
            System.out.println("No files found.");
            return;
        }
        for (File f : files) {
            System.out.println((f.isDirectory() ? "<DIR> " : "      ") + f.getName());
        }
    }
}
