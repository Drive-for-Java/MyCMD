package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

import java.io.IOException;
import java.nio.file.*;

/**
 * LsCommand - lists files and directories in the shell's current directory.
 */
public class LsCommand implements Command {

    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        Path dir = context.getCurrentDir().toPath();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path path : stream) {
                if (Files.isDirectory(path)) {
                    System.out.println("[DIR]  " + path.getFileName());
                } else {
                    System.out.println("       " + path.getFileName());
                }
            }
        }
    }

    @Override
    public String description() {
        return "List files and directories in the current directory.";
    }

    @Override
    public String usage() {
        return "ls";
    }
}
