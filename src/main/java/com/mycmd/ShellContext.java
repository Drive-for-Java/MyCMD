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
