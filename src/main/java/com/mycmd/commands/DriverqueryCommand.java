package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Displays a list of all installed device drivers.
 *
 * <p>Usage: - driverquery : Display list of drivers - driverquery /v : Display verbose information
 */
public class DriverqueryCommand implements Command {

  @Override
  public void execute(String[] args, ShellContext context) throws IOException {
    String os = System.getProperty("os.name").toLowerCase();

    if (!os.contains("win")) {
      System.out.println("driverquery is only available on Windows systems.");
      return;
    }

    try {

      List<String> command = new ArrayList<>();
      command.add("driverquery");
      if (args != null && args.length > 0) {
        command.addAll(Arrays.asList(args));
      }

      ProcessBuilder pb = new ProcessBuilder(command);
      Process process = pb.start();

      Thread errorGobbler =
          new Thread(
              () -> {
                try (BufferedReader errReader =
                    new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                  String errLine;
                  while ((errLine = errReader.readLine()) != null) {
                    System.err.println(errLine);
                  }
                } catch (IOException e) {
                  System.err.println("Error reading process error stream: " + e.getMessage());
                }
              },
              "driverquery-error-gobbler");
      errorGobbler.setDaemon(true);
      errorGobbler.start();

      try (BufferedReader reader =
          new BufferedReader(new InputStreamReader(process.getInputStream()))) {
        String line;
        while ((line = reader.readLine()) != null) {
          System.out.println(line);
        }
      }

      boolean finished;
      try {
        finished = process.waitFor(30, TimeUnit.SECONDS);
      } catch (InterruptedException ie) {
        Thread.currentThread().interrupt();
        process.destroyForcibly();
        throw new IOException("Interrupted while waiting for driverquery process", ie);
      }

      if (!finished) {
        process.destroyForcibly();
        System.out.println("Command timed out.");
      } else {
        int exitCode = process.exitValue();
        if (exitCode != 0) {
          System.out.println("Command exited with code: " + exitCode);
        }
      }

      try {
        errorGobbler.join(1000);
      } catch (InterruptedException ie) {
        Thread.currentThread().interrupt();
      }

    } catch (Exception e) {
      System.out.println("Error executing driverquery: " + e.getMessage());
    }
  }

  @Override
  public String description() {
    return "Displays a list of all installed device drivers.";
  }

  @Override
  public String usage() {
    return "driverquery [/v]";
  }
}
