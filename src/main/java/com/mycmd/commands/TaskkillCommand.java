package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Terminates one or more processes by process ID or image name.
 *
 * <p>Usage: - taskkill /PID processid : Kill by process ID - taskkill /IM imagename : Kill by image
 * name - taskkill /F /IM imagename : Force kill
 */
public class TaskkillCommand implements Command {

  @Override
  public void execute(String[] args, ShellContext context) throws IOException {
    if (args.length == 0) {
      System.out.println("TASKKILL [/S system [/U username [/P [password]]]]");
      System.out.println("         { [/FI filter] [/PID processid | /IM imagename] } [/T] [/F]");
      System.out.println("\nDescription:");
      System.out.println(
          "    This tool is used to terminate tasks by process id (PID) or image name.");
      System.out.println("\nExamples:");
      System.out.println("    taskkill /IM notepad.exe");
      System.out.println("    taskkill /PID 1234");
      System.out.println("    taskkill /F /IM chrome.exe");
      return;
    }

    String os = System.getProperty("os.name").toLowerCase();

    if (os.contains("win")) {
      try {
        StringBuilder cmdBuilder = new StringBuilder("taskkill");
        for (String arg : args) {
          cmdBuilder.append(" ").append(arg);
        }

        Process process = Runtime.getRuntime().exec(cmdBuilder.toString());
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
        System.out.println("Error executing taskkill: " + e.getMessage());
      }
    } else {
      // Unix-like systems - use kill command
      System.out.println("On Unix-like systems, use 'kill' command instead.");
      System.out.println("Example: kill -9 <PID>");
    }
  }

  @Override
  public String description() {
    return "Terminates one or more processes.";
  }

  @Override
  public String usage() {
    return "taskkill [/F] [/PID processid | /IM imagename]";
  }
}
