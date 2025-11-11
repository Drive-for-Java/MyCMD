package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Displays active TCP connections and listening ports.
 *
 * <p>Usage: - netstat : Display active connections - netstat -a : Display all connections and
 * listening ports
 */
public class NetstatCommand implements Command {

  @Override
  public void execute(String[] args, ShellContext context) throws IOException {
    try {
      StringBuilder cmdBuilder = new StringBuilder("netstat");
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
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }

      process.waitFor();

    } catch (Exception e) {
      System.out.println("Error executing netstat: " + e.getMessage());
    }
  }

  @Override
  public String description() {
    return "Displays active TCP connections and listening ports.";
  }

  @Override
  public String usage() {
    return "netstat [-a] [-n] [-o] [-p protocol]";
  }
}
