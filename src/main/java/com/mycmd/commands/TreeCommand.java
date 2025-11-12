package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.File;

/**
 * Displays the directory tree structure in a graphical ASCII format.
 *
 * <p>This command recursively traverses the directory tree starting from the current working
 * directory and displays files and subdirectories using box-drawing characters to show the
 * hierarchical structure. Hidden files are excluded from the output.
 *
 * <p>Usage: tree
 *
 * <p>The output uses Unicode characters for tree branches: - Vertical lines and horizontal
 * connectors for hierarchy - Corner characters for the last item in each directory
 *
 * <p>The command recursively processes subdirectories to show the complete directory structure
 * beneath the current location.
 */
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

    @Override
    public String description() {
        return "Display the directory tree structure.";
    }

    @Override
    public String usage() {
        return "tree";
    }
}
