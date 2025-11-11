package com.mycmd;

import java.util.HashMap;
import java.util.Map;

/** Registers and retrieves commands by name. */
public class CommandRegistry {
  private final Map<String, Command> commands = new HashMap<>();

  public void register(String name, Command cmd) {
    commands.put(name.toLowerCase(), cmd);
  }

  public Command get(String name) {
    return commands.get(name.toLowerCase());
  }

  public Map<String, Command> getAll() {
    return commands;
  }
}
