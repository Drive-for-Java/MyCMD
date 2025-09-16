package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

public class ClsCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}
