package com.mycmd.commands;

import com.mycmd.*;
import java.io.IOException;
import java.util.Map;

public class SetCommand implements Command {

  @Override
  public void execute(String[] args, ShellContext context) throws IOException {
    if (args.length == 0) {
      // Print all environment variables
      for (Map.Entry<String, String> entry : context.getEnvVars().entrySet()) {
        System.out.println(entry.getKey() + "=" + entry.getValue());
      }
      return;
    }

    String arg = args[0];
    if (arg.contains("=")) {
      String[] parts = arg.split("=", 2);
      String key = parts[0].trim();
      String value = parts[1].trim();
      context.setEnvVar(key, value);
      System.out.println("Variable set: " + key + "=" + value);
    } else {
      String value = context.getEnvVar(arg);
      if (value != null) {
        System.out.println(arg + "=" + value);
      } else {
        System.out.println("Variable not found: " + arg);
      }
    }
  }

  @Override
  public String description() {
    return "Displays, sets, or retrieves shell environment variables.";
  }

  @Override
  public String usage() {
    return "Usage: set [variable[=value]]";
  }
}
