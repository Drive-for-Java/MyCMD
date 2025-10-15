package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.File;

/**
 * Lists files and directories in the current working directory.
 * 
 * This command displays all files and subdirectories within the current
 * directory. Directories are prefixed with angle brackets and "DIR" label,
 * while files are displayed with spacing for alignment.
 * 
 * Usage: dir
 * 
 * Output format:
 * - Directories: angle-bracket-DIR-angle-bracket followed by directory name
 * - Files: Six spaces followed by file name
 * 
 * If the directory is empty or cannot be read, an appropriate message is displayed.
 */
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
