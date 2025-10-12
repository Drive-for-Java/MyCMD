package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.File;

public class TreeCommand implements Command {
    public void execute(String[] args, ShellContext context) {
        File[] files = context.getCurrentDir().listFiles();

        if (files == null || files.length == 0) {
            System.out.println("No files found.");
            return;
        }

        System.out.println(context.getCurrentDir().getAbsolutePath());
        printDirectory(files, "", true);
        System.out.println();
    }

    private void printDirectory(File[] files, String prefix, boolean isLast) {
        if (files == null || files.length == 0) return;

        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isHidden()) continue;

            boolean last = (i == files.length - 1);
            System.out.println(prefix + (last ? "└───" : "├───") + f.getName());

            if (f.isDirectory()) {
                String newPrefix = prefix + (last ? "    " : "│   ");
                printDirectory(f.listFiles(), newPrefix, last);
            }
        }
    }
}
