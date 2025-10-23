package com.mycmd;

import java.io.*;
import java.util.*;

public class ShellContext {
    private File currentDir;
    private List<String> history;
    private Map<String, String> aliases;
    private static final String ALIAS_FILE = ".mycmd_aliases";
    private static final int MAX_HISTORY = 100;

    public ShellContext() {
        this.currentDir = new File(System.getProperty("user.dir"));
        this.history = new ArrayList<>();
        this.aliases = new HashMap<>();
        loadAliases();
    }

    public File getCurrentDir() {
        return currentDir;
    }

    public void setCurrentDir(File dir) {
        this.currentDir = dir;
    }

    public void addToHistory(String command) {
        history.add(command);
        if (history.size() > MAX_HISTORY) {
            history.remove(0);
        }
    }

    public List<String> getHistory() {
        return new ArrayList<>(history);
    }

    public void clearHistory() {
        history.clear();
    }

    // Alias management methods
    public void addAlias(String name, String command) {
        aliases.put(name, command);
        saveAliases();
    }

    public void removeAlias(String name) {
        aliases.remove(name);
        saveAliases();
    }

    public String getAlias(String name) {
        return aliases.get(name);
    }

    public Map<String, String> getAliases() {
        return new HashMap<>(aliases);
    }

    public boolean hasAlias(String name) {
        return aliases.containsKey(name);
    }

    private void loadAliases() {
        File aliasFile = new File(System.getProperty("user.home"), ALIAS_FILE);
        if (!aliasFile.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(aliasFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    String command = parts[1].trim();
                    aliases.put(name, command);
                }
            }
        } catch (IOException e) {
            System.err.println("Warning: Could not load aliases: " + e.getMessage());
        }
    }

    private void saveAliases() {
        File aliasFile = new File(System.getProperty("user.home"), ALIAS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(aliasFile))) {
            writer.write("# MyCMD Aliases Configuration\n");
            writer.write("# Format: aliasName=command\n\n");
            for (Map.Entry<String, String> entry : aliases.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Warning: Could not save aliases: " + e.getMessage());
        }
    }

    /**
     * Resolve the given path (absolute or relative) to a File using the current directory.
     * If the provided path is absolute, returns it directly; otherwise returns a File rooted at currentDir.
     */
    public File resolvePath(String path) {
        if (path == null || path.trim().isEmpty()) {
            return currentDir;
        }
        File f = new File(path);
        if (f.isAbsolute()) {
            return f;
        } else {
            return new File(currentDir, path);
        }
    }
}
