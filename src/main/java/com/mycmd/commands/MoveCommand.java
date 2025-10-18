package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

import java.io.IOException;
import java.nio.file.*;


/**
 * Moves or renames a file or directory from source to destination.
 * 
 * This command uses the Java NIO Files.move() operation to relocate files
 * or directories. It supports both moving files between directories and
 * renaming them. If the destination already exists, it will be replaced.
 * 
 * Usage: move source destination
 * 
 * Both source and destination paths are resolved relative to the current
 * working directory using ShellContext.resolvePath(). The command verifies
 * that the source exists before attempting the move operation.
 */
public class MoveCommand implements Command {
    @Override
    // 1. "throws IOException" foi REMOVIDO daqui
    public void execute(String[] args, ShellContext context) {
        if (args.length < 2) {
            System.out.println("Usage: move <source> <destination>");
            return;
        }

        // 2. As chamadas ".toPath()" foram REMOVIDAS daqui
        Path source = context.resolvePath(args[0]);
        Path destination = context.resolvePath(args[1]);

        if (!Files.exists(source)) {
            System.out.println("The system cannot find the file specified.");
            return;
        }

        try {
            Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Moved: " + source.getFileName() + " -> " + destination);
        } catch (IOException e) {
            // O erro já é tratado aqui, por isso não precisamos do "throws"
            System.out.println("Error moving file: " + e.getMessage());
        }
    }
    @Override
    public String description() {
        return "Moves or renames a file or directory.";
    }

    @Override
    public String usage() {
        return "Usage: move <source> <destination>";
    }
}