package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

/**
 * Clears the stored command history.
 *
 * <p>This command removes all previously executed commands from the shell's command history. After
 * execution, the history list will be empty and the history command will show no previous commands.
 *
 * <p>Usage: - clearhistory : Clear all stored command history
 *
 * <p>This is useful for privacy purposes or when you want to start fresh with a clean command
 * history. The history is cleared immediately and cannot be recovered.
 */
public class ClearHistoryCommand implements Command {
  @Override
  public void execute(String[] args, ShellContext context) {
    context.clearHistory();
    System.out.println("Command history cleared.");
  }

  @Override
  public String description() {
    return "Clear the stored command history.";
  }

  @Override
  public String usage() {
    return "clearhistory";
  }
}
