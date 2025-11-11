package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

/**
 * Displays the version information of the MyCMD shell.
 *
 * <p>This command outputs the name and version number of the MyCMD application. It provides a
 * simple way for users to verify which version of the shell they are running.
 *
 * <p>Usage: ver
 *
 * <p>Note: This command does not accept any arguments and always displays the same version string.
 */
public class VersionCommand implements Command {
  @Override
  public void execute(String[] args, ShellContext context) {
    System.out.println("MyCMD Java Shell v1.0");
  }

  @Override
  public String description() {
    return "Display the version information of the MyCMD shell.";
  }

  @Override
  public String usage() {
    return "ver";
  }
}
