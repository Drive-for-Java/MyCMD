package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

public class HistoryCommand implements Command {

    @Override
    public void execute(String[] args, ShellContext context) {
        // Get the command history from the context
        var history = context.getCommandHistory();
        
        if (history.isEmpty()) {
            System.out.println("No commands in history.");
            return;
        }
        
        // Display the history with numbers
        for (int i = 0; i < history.size(); i++) {
            System.out.println((i + 1) + ". " + history.get(i));
        }
    }
}
