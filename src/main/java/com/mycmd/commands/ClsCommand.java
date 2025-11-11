package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.IOException;

/**
 * Clears the console screen using the native OS command.
 *
 * <p>This command clears the terminal screen by executing the 'cls' command on Windows or the
 * 'clear' command on Unix-like systems (macOS, Linux). This provides a true clear-screen
 * functionality.
 *
 * <p>Usage: cls
 *
 * <p>Note: This command does not accept any arguments.
 */
public class ClsCommand implements Command {

  @Override
  public void execute(String[] args, ShellContext context) {
    try {
      String os = System.getProperty("os.name").toLowerCase();
      if (os.contains("win")) { // For Windows Users
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      } else { // For other Operation Systems
        new ProcessBuilder("clear").inheritIO().start().waitFor();
      }
    } catch (InterruptedException e) { // Restore the interrupted status
      Thread.currentThread().interrupt();
      System.out.println("Error while clearing the screen: " + e.getMessage());
    } catch (IOException e) {
      System.out.println("Error while clearing the screen: " + e.getMessage());
    }
  }

  @Override
  public String description() {
    return "Clear the console screen.";
  }

  @Override
  public String usage() {
    return "cls";
  }
}
