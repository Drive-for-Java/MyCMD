package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.IOException;

/**
 * Replaces files in a directory.
 *
 * <p>Usage: - replace source destination : Replace files
 */
public class ReplaceCommand implements Command {

  @Override
  public void execute(String[] args, ShellContext context) throws IOException {
    if (args.length < 2) {
      System.out.println("Replaces files.");
      System.out.println("\nREPLACE [drive1:][path1]filename [drive2:][path2] [/A] [/P] [/R] [/W]");
      System.out.println(
          "REPLACE [drive1:][path1]filename [drive2:][path2] [/P] [/R] [/S] [/W] [/U]");
      System.out.println("\n  [drive1:][path1]filename Specifies the source file or files.");
      System.out.println("  [drive2:][path2]         Specifies the directory where files");
      System.out.println("                           are to be replaced.");
      System.out.println("  /A                       Adds new files to destination directory.");
      System.out.println("  /P                       Prompts for confirmation.");
      System.out.println("  /R                       Replaces read-only files as well.");
      System.out.println("  /S                       Replaces files in all subdirectories.");
      System.out.println("  /W                       Waits for you to insert a disk.");
      System.out.println("  /U                       Replaces only files that are older.");
      return;
    }

    System.out.println("REPLACE command simulation.");
    System.out.println(
        "This is a simplified version. Use COPY or XCOPY for similar functionality.");
    System.out.println("Source: " + args[0]);
    System.out.println("Destination: " + args[1]);
  }

  @Override
  public String description() {
    return "Replaces files in a directory.";
  }

  @Override
  public String usage() {
    return "replace [source] [destination] [/A] [/P] [/R] [/S]";
  }
}
