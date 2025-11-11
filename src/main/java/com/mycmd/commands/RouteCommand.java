package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * Displays and modifies entries in the local IP routing table.
 *
 * <p>Usage: - route print : Display routing table - route add : Add route
 */
public class RouteCommand implements Command {

  @Override
  public void execute(String[] args, ShellContext context) throws IOException {
    if (args.length == 0) {
      System.out.println("Manipulates network routing tables.");
      System.out.println("\nROUTE [-f] [-p] [command [destination] [MASK netmask] [gateway]]");
      System.out.println("\n  -f           Clears the routing tables of all gateway entries.");
      System.out.println("  -p           Makes a route persistent across boots.");
      System.out.println("  command      One of these:");
      System.out.println("                 PRINT     Prints a route");
      System.out.println("                 ADD       Adds a route");
      System.out.println("                 DELETE    Deletes a route");
      System.out.println("                 CHANGE    Modifies an existing route");
      System.out.println("\nNote: Administrator privileges required for modification.");
      return;
    }

    try {
      StringBuilder cmdBuilder = new StringBuilder("route");
      for (String arg : args) {
        cmdBuilder.append(" ").append(arg);
      }

      ProcessBuilder pb = new ProcessBuilder();
      String os = System.getProperty("os.name").toLowerCase();

      if (os.contains("win")) {
        pb.command("cmd.exe", "/c", cmdBuilder.toString());
      } else {
        pb.command("sh", "-c", cmdBuilder.toString());
      }

      Process process = pb.start();
      try (BufferedReader reader =
              new BufferedReader(new InputStreamReader(process.getInputStream()));
          BufferedReader errorReader =
              new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
        String line;
        while ((line = reader.readLine()) != null) {
          System.out.println(line);
        }

        while ((line = errorReader.readLine()) != null) {
          System.err.println(line);
        }
      }

      if (!process.waitFor(30, TimeUnit.SECONDS)) {
        process.destroyForcibly();
        System.out.println("Command timed out.");
      }

    } catch (Exception e) {
      System.out.println("Error executing route: " + e.getMessage());
    }
  }

  @Override
  public String description() {
    return "Displays and modifies entries in the local IP routing table.";
  }

  @Override
  public String usage() {
    return "route [print | add | delete | change] [options]";
  }
}
