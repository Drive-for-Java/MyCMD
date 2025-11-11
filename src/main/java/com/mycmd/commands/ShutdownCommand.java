package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * Shuts down or restarts the computer.
 *
 * <p>Usage: - shutdown /s : Shutdown - shutdown /r : Restart - shutdown /l : Log off
 */
public class ShutdownCommand implements Command {

  @Override
  public void execute(String[] args, ShellContext context) throws IOException {
    if (args.length == 0) {
      System.out.println("Usage: shutdown [/i | /l | /s | /r | /a | /p | /h | /e]");
      System.out.println(
          "                [/f] [/m \\\\computer][/t xxx][/d [p:]xx:yy [/c \"comment\"]]");
      System.out.println("\n    No args    Display help.");
      System.out.println("    /i         Display the GUI interface (must be the first option).");
      System.out.println("    /l         Log off (cannot be used with /m or /d options).");
      System.out.println("    /s         Shutdown the computer.");
      System.out.println("    /r         Shutdown and restart the computer.");
      System.out.println("    /a         Abort a system shutdown.");
      System.out.println("    /p         Turn off the local computer with no time-out or warning.");
      System.out.println("    /h         Hibernate the local computer.");
      System.out.println("    /f         Force running applications to close without warning.");
      System.out.println("    /t xxx     Set time-out period before shutdown to xxx seconds.");
      System.out.println("\nNote: Administrator privileges required.");
      System.out.println("Warning: This command will actually shutdown/restart your system!");
      return;
    }

    System.out.println("WARNING: This will execute a real shutdown command!");
    System.out.print("Are you sure you want to continue? (yes/no): ");

    try (java.util.Scanner scanner = new java.util.Scanner(System.in)) {
      String confirmation = scanner.nextLine().trim().toLowerCase();
      if (!confirmation.equals("yes")) {
        System.out.println("Shutdown cancelled.");
        return;
      }
      }
    }

    try {
      StringBuilder cmdBuilder = new StringBuilder("shutdown");
      for (String arg : args) {
        cmdBuilder.append(" ").append(arg);
      }

      ProcessBuilder pb = new ProcessBuilder();
      String os = System.getProperty("os.name").toLowerCase();

      if (os.contains("win")) {
        pb.command("cmd.exe", "/c", cmdBuilder.toString());
      } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
        pb.command("sh", "-c", cmdBuilder.toString());
      }

      Process process = pb.start();
      try (java.util.Scanner scanner = new java.util.Scanner(System.in)) {
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if (!confirmation.equals("yes")) {
          System.out.println("Shutdown cancelled.");
          return;
        }
      }

    } catch (Exception e) {
      System.out.println("Error executing shutdown: " + e.getMessage());
      System.out.println("Administrator privileges may be required.");
    }
  }

  @Override
  public String description() {
    return "Shuts down or restarts the computer.";
  }

  @Override
  public String usage() {
    return "shutdown [/s | /r | /l] [/f] [/t seconds]";
  }
}
