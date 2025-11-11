package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.*;

/**
 * Displays the contents of a text file to the console.
 *
 * <p>This command reads and displays the contents of a specified text file line by line. The file
 * path is resolved relative to the current working directory. It's similar to the Unix cat command
 * or Windows type command.
 *
 * <p>Usage: type filename
 *
 * <p>The command verifies that the specified path exists and is a regular file before attempting to
 * read it. It uses BufferedReader for efficient line-by-line reading and properly closes the file
 * after reading.
 *
 * <p>Note: Best suited for text files. Binary files may produce garbled output.
 */
public class TypeCommand implements Command {
  @Override
  public void execute(String[] args, ShellContext context) {
    if (args.length == 0) {
      System.out.println("Usage: " + usage());
      return;
    }
    File file = new File(context.getCurrentDir(), args[0]);
    if (!file.exists() || !file.isFile()) {
      System.out.println("File not found.");
      return;
    }
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        System.out.println(line);
      }
    } catch (IOException e) {
      System.out.println("Error reading file: " + e.getMessage());
    }
  }

  @Override
  public String description() {
    return "Display the contents of a text file.";
  }

  @Override
  public String usage() {
    return "type <file_name>";
  }
}
