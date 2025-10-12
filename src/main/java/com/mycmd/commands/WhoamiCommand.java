package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

public class WhoamiCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        System.out.println(System.getProperty("user.name"));
    }
}
