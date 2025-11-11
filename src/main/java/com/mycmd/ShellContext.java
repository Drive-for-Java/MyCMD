package com.mycmd;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ShellContext {

  private File currentDir;
  private final Map<String, String> aliases;

  private Scanner scanner;

  public ShellContext() {
    this.currentDir = new File(System.getProperty("user.dir"));
    this.aliases = new HashMap<>();
  }

  public File getCurrentDir() {
    return currentDir;
  }

  public void setCurrentDir(File dir) {
    if (dir != null && dir.exists() && dir.isDirectory()) {
      this.currentDir = dir;
    }
  }

  public Map<String, String> getAliases() {
    return aliases;
  }

  public void addAlias(String name, String command) {
    aliases.put(name, command);
  }

  public String resolveAlias(String cmd) {
    return aliases.getOrDefault(cmd, cmd);
  }
}
