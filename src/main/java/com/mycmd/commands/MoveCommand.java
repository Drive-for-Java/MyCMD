package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class MoveCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: move <source> <destination>");
            return;
        }

        Path source = context.resolvePath(args[0]).toPath();
        Path destination = context.resolvePath(args[1]).toPath();

        if (!Files.exists(source)) {
            System.out.println("The system cannot find the file specified.");
            return;
        }

        try {
            Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Moved: " + source.getFileName() + " -> " + destination);
        } catch (IOException e) {
            System.out.println("Error moving file: " + e.getMessage());
        }
    }
}
