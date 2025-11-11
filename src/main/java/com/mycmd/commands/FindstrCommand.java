package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Searches for strings in files using regular expressions.
 *
 * <p>Usage: - findstr "pattern" filename : Search for pattern in file - findstr /I "pattern" file :
 * Case-insensitive search
 */
public class FindstrCommand implements Command {

  @Override
  public void execute(String[] args, ShellContext context) throws IOException {
    if (args.length < 2) {
      System.out.println("Searches for strings in files.");
      System.out.println("\nFINDSTR [/B] [/E] [/L] [/R] [/S] [/I] [/X] [/V] [/N] [/M] [/O] [/P]");
      System.out.println("        [/F:file] [/C:string] [/G:file] [/D:dir list] [strings]");
      System.out.println("        [[drive:][path]filename[ ...]]");
      System.out.println("\n  /I         Specifies that the search is not to be case-sensitive.");
      System.out.println("  /N         Prints the line number before each line that matches.");
      System.out.println("  /R         Uses search strings as regular expressions.");
      System.out.println(
          "  /S         Searches for matching files in the current directory and all");
      System.out.println("             subdirectories.");
      System.out.println("  strings    Text to be searched for.");
      System.out.println("  [drive:][path]filename");
      System.out.println("             Specifies a file or files to search.");
      return;
    }

    boolean caseInsensitive = false;
    boolean showLineNumbers = false;
    boolean useRegex = false;
    boolean recursive = false;
    int argIndex = 0;

    // Parse flags
    while (argIndex < args.length && args[argIndex].startsWith("/")) {
      String flag = args[argIndex].toUpperCase();
      if (flag.equals("/I")) caseInsensitive = true;
      else if (flag.equals("/N")) showLineNumbers = true;
      else if (flag.equals("/R")) useRegex = true;
      else if (flag.equals("/S")) recursive = true;
      argIndex++;
    }

    if (argIndex >= args.length) {
      System.out.println("Missing search string.");
      return;
    }

    String searchString = args[argIndex++].replace("\"", "");

    if (argIndex >= args.length) {
      System.out.println("Missing filename.");
      return;
    }

    Pattern pattern = null;
    if (useRegex) {
      int flags = caseInsensitive ? Pattern.CASE_INSENSITIVE : 0;
      pattern = Pattern.compile(searchString, flags);
    }

    // Process files
    for (int i = argIndex; i < args.length; i++) {
      String filename = args[i];
      java.io.File file = new java.io.File(filename);

      if (!file.isAbsolute()) {
        file = new java.io.File(context.getCurrentDir(), filename);
      }

      if (!file.exists()) {
        System.out.println("File not found - " + filename);
        continue;
      }

      searchInFile(file, searchString, pattern, caseInsensitive, showLineNumbers, useRegex);
    }
  }

  private void searchInFile(
      java.io.File file,
      String searchString,
      Pattern pattern,
      boolean caseInsensitive,
      boolean showLineNumbers,
      boolean useRegex)
      throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String line;
      int lineNumber = 0;

      while ((line = reader.readLine()) != null) {
        lineNumber++;
        boolean matches = false;

        if (useRegex && pattern != null) {
          Matcher matcher = pattern.matcher(line);
          matches = matcher.find();
        } else {
          String compareLine = caseInsensitive ? line.toLowerCase() : line;
          String compareString = caseInsensitive ? searchString.toLowerCase() : searchString;
          matches = compareLine.contains(compareString);
        }

        if (matches) {
          if (showLineNumbers) {
            System.out.println(file.getName() + ":" + lineNumber + ":" + line);
          } else {
            System.out.println(file.getName() + ":" + line);
          }
        }
      }
    }
  }

  @Override
  public String description() {
    return "Searches for strings in files using regular expressions.";
  }

  @Override
  public String usage() {
    return "findstr [/I] [/N] [/R] \"pattern\" filename";
  }
}
