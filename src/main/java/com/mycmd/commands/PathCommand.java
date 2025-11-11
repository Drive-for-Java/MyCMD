package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.IOException;

/**
 * Displays or sets the command path.
 *
 * <p>Usage: - path : Display current path - path C:\dir : Set path
 */
public class PathCommand implements Command {

  @Override
  public void execute(String[] args, ShellContext context) throws IOException {
    if (args.length == 0) {
      // Display current PATH
      String path = System.getenv("PATH");
      if (path != null) {
        System.out.println("PATH=" + path);
      } else {
        System.out.println("No Path");
      }
      return;
    }

    System.out.println("Note: Modifying PATH in MyCMD only affects the current shell session.");
    System.out.println(
        "To permanently modify PATH, use Windows System Properties or setx command.");

    String newPath = String.join(" ", args);

    if (newPath.equalsIgnoreCase(";")) {
      System.out.println("PATH cleared (session only)");
    } else {
      System.out.println("PATH set to: " + newPath + " (session only)");
    }
  }

  @Override
  public String description() {
    return "Displays or sets the command path.";
  }

  @Override
  public String usage() {
    return "path [path]";
  }
}
