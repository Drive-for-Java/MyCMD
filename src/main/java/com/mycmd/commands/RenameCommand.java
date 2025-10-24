package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Implements the "rename" (or "ren") command for MyCMD.
 * Usage: rename <oldName> <newName>
 */
public class RenameCommand implements Command {

    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: " + usage());
            return;
        }

        File oldFile = context.resolvePath(args[0]);
        File newFile = context.resolvePath(args[1]);

        if (!oldFile.exists()) {
            System.out.println("The system cannot find the file specified: " + args[0]);
            return;
        }

        try {
            Files.move(oldFile.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File renamed from " + oldFile.getName() + " to " + newFile.getName());
        } catch (IOException e) {
            throw new IOException("Failed to rename file: " + e.getMessage(), e);
        }
    }

    @Override
    public String description() {
        return "Renames a file or directory.";
    }

    @Override
    public String usage() {
        return "rename <oldName> <newName>";
    }
}
