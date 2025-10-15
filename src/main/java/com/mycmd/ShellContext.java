package com.mycmd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ShellContext {
    private File currentDir;
    private List<String> commandHistory;
    private static final int MAX_HISTORY = 10;

    public ShellContext() {
        this.currentDir = new File(System.getProperty("user.dir"));
        this.commandHistory = new ArrayList<>();
    }

    public File getCurrentDir() {
        return currentDir;
    }

    public void setCurrentDir(File dir) {
        this.currentDir = dir;
    }

    public List<String> getCommandHistory() {
        return commandHistory;
    }

    public void addToHistory(String command) {
        if (command != null && !command.trim().isEmpty()) {
            commandHistory.add(command.trim());
            if (commandHistory.size() > MAX_HISTORY) {
                commandHistory.remove(0);
            }
        }
    }
}
