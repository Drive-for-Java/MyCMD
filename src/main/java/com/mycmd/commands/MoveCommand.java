package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.IOException;
import java.nio.file.*;

/**
 * Moves or renames a file or directory from source to destination.
 *
 * <p>This command uses the Java NIO Files.move() operation to relocate files or directories. It
 * supports both moving files between directories and renaming them. If the destination already
 * exists, it will be replaced.
 *
 * <p>Usage: move source destination
 *
 * <p>Both source and destination paths are resolved relative to the current working directory using
 * ShellContext.resolvePath(). The command verifies that the source exists before attempting the
 * move operation.
 */
public class MoveCommand implements Command {
  @Override
  public void execute(String[] args, ShellContext context) throws IOException {
    if (args.length < 2) {
      System.out.println("Usage: " + usage());
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
