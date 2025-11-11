package com.mycmd;

import java.io.File;
import java.util.*;

/**
 * Context object that holds the shell's state including current directory, environment variables,
 * command history, aliases, and shared Scanner.
 */
public class ShellContext {
  private File currentDir;
  private final Map<String, String> environment;
  private final List<String> history;
  private final Map<String, String> aliases;
  private Scanner scanner;
  private long startTime;

  public ShellContext() {
    this.currentDir = new File(System.getProperty("user.dir"));
    this.environment = new HashMap<>(System.getenv());
    this.history = new ArrayList<>();
    this.aliases = new HashMap<>();
    this.scanner = null;
    this.startTime = System.currentTimeMillis();
  }

  // ==================== Scanner Management ====================

  /**
   * Set the shared Scanner instance for all commands to use. Should only be called once by App.java
   * during initialization.
   */
  public void setScanner(Scanner scanner) {
    this.scanner = scanner;
  }

  /**
   * Get the shared Scanner instance. All commands should use this instead of creating their own
   * Scanner.
   *
   * @return the shared Scanner instance
   * @throws IllegalStateException if Scanner hasn't been initialized
   */
  public Scanner getScanner() {
    if (scanner == null) {
      throw new IllegalStateException("Scanner not initialized in ShellContext");
    }
    return scanner;
  }

  // ==================== Directory Management ====================

  public File getCurrentDir() {
    return currentDir;
  }

  public void setCurrentDir(File dir) {
    if (dir != null && dir.exists() && dir.isDirectory()) {
      this.currentDir = dir;
    }
  }

  /**
   * Resolve a path relative to the current directory. If the path is absolute, return it as-is. If
   * relative, resolve it against the current directory.
   */
  public File resolvePath(String path) {
    File file = new File(path);
    if (file.isAbsolute()) {
      return file;
    }
    return new File(currentDir, path);
  }

  // ==================== Environment Variables ====================

  public String getEnvVar(String key) {
    return environment.get(key);
  }

  public void setEnvVar(String key, String value) {
    environment.put(key, value);
  }

  public Map<String, String> getAllEnvVars() {
    return new HashMap<>(environment);
  }

  /** Legacy method name support for compatibility. */
  public Map<String, String> getEnvVars() {
    return getAllEnvVars();
  }

  // ==================== Command History ====================

  public void addToHistory(String command) {
    history.add(command);
  }

  public List<String> getHistory() {
    return new ArrayList<>(history);
  }

  /** Legacy method name support for compatibility. */
  public List<String> getCommandHistory() {
    return getHistory();
  }

  public void clearHistory() {
    history.clear();
  }

  // ==================== Aliases ====================

  /** Get all aliases (for compatibility with existing code). */
  public Map<String, String> getAliases() {
    return new HashMap<>(aliases);
  }

  /** Add an alias (for compatibility with existing code). */
  public void addAlias(String name, String command) {
    aliases.put(name, command);
  }

  /** Resolve an alias (for compatibility with existing code). */
  public String resolveAlias(String cmd) {
    return aliases.getOrDefault(cmd, cmd);
  }

  /** Set an alias. */
  public void setAlias(String alias, String command) {
    aliases.put(alias, command);
  }

  /** Get alias command. */
  public String getAlias(String alias) {
    return aliases.get(alias);
  }

  /** Check if alias exists. */
  public boolean hasAlias(String alias) {
    return aliases.containsKey(alias);
  }

  /** Remove an alias. */
  public void removeAlias(String alias) {
    aliases.remove(alias);
  }

  /** Get all aliases. */
  public Map<String, String> getAllAliases() {
    return new HashMap<>(aliases);
  }

  // ==================== Start Time (for uptime command) ====================

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }
}
