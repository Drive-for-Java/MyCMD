package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.util.Map;

public class HelpCommand implements Command {
    private final Map<String, Command> commands;

    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void execute(String[] args, ShellContext context) {
        System.out.println("Available commands:");
        for (String key : commands.keySet()) {
            System.out.println(" - " + key);
        }
    }
}
