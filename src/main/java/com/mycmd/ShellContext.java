package com.mycmd;

import java.io.File;

public class ShellContext {
    private File currentDir;

    public ShellContext() {
        this.currentDir = new File(System.getProperty("user.dir"));
    }

    public File getCurrentDir() {
        return currentDir;
    }

    public void setCurrentDir(File dir) {
        this.currentDir = dir;
    }
}
