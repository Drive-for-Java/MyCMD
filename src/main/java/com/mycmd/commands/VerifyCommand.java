package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.IOException;

/**
 * Enables or disables file verification.
 *
 * <p>Usage: - verify : Display current verify setting - verify ON : Enable verification - verify
 * OFF : Disable verification
 */
public class VerifyCommand implements Command {

  private static boolean verifyEnabled = false;

  @Override
  public void execute(String[] args, ShellContext context) throws IOException {
    if (args.length == 0) {
      System.out.println("VERIFY is " + (verifyEnabled ? "on" : "off") + ".");
      return;
    }

    String option = args[0].toUpperCase();

    if (option.equals("ON")) {
      verifyEnabled = true;
      System.out.println("VERIFY is on.");
    } else if (option.equals("OFF")) {
      verifyEnabled = false;
      System.out.println("VERIFY is off.");
    } else {
      System.out.println("Must specify ON or OFF.");
    }
  }

  @Override
  public String description() {
    return "Enables or disables file verification.";
  }

  @Override
  public String usage() {
    return "verify [ON | OFF]";
  }
}
