package com.mycmd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    public Path resolvePath(String path) {
        Path inputPath = Paths.get(path);

        if (inputPath.isAbsolute()) {
            return inputPath;
        }

        return Paths.get(this.getCurrentDir().getAbsolutePath()).resolve(inputPath);
    }
}
