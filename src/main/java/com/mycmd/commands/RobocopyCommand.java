package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Robust File Copy - Advanced file copy utility.
 *
 * <p>Usage: - robocopy source destination [/S] [/E] [/MIR]
 */
public class RobocopyCommand implements Command {

  @Override
  public void execute(String[] args, ShellContext context) throws IOException {
    if (args.length == 0) {
      System.out.println("ROBOCOPY :: Robust File Copy");
      System.out.println("\nUsage: robocopy <Source> <Destination> [<File>[ ...]] [<Options>]");
      System.out.println("\nCommon Options:");
      System.out.println("  /S           Copy subdirectories (excluding empty ones)");
      System.out.println("  /E           Copy subdirectories (including empty ones)");
      System.out.println("  /MIR         Mirror a directory tree");
      System.out.println("  /PURGE       Delete dest files/dirs that no longer exist in source");
      System.out.println("  /COPYALL     Copy all file info");
      System.out.println("  /R:n         Number of retries on failed copies");
      System.out.println("  /W:n         Wait time between retries");
      return;
    }

    String os = System.getProperty("os.name").toLowerCase();

    if (os.contains("win")) {
      try {
        StringBuilder cmdBuilder = new StringBuilder("robocopy");
        for (String arg : args) {
          cmdBuilder.append(" \"").append(arg).append("\"");
        }

        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", cmdBuilder.toString());
        pb.directory(context.getCurrentDir());
        Process process = pb.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader errorReader =
            new BufferedReader(new InputStreamReader(process.getErrorStream()));

        String line;
        while ((line = reader.readLine()) != null) {
          System.out.println(line);
        }

        while ((line = errorReader.readLine()) != null) {
          System.err.println(line);
        }

        process.waitFor();

      } catch (Exception e) {
        System.out.println("Error executing robocopy: " + e.getMessage());
      }
    } else {
      System.out.println("ROBOCOPY is not available on this system. Use 'rsync' instead.");
    }
  }

  @Override
  public String description() {
    return "Robust File Copy - Advanced file copy utility.";
  }

  @Override
  public String usage() {
    return "robocopy <Source> <Destination> [/S] [/E] [/MIR]";
  }
}
