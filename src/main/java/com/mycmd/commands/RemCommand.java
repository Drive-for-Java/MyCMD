package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.IOException;

/**
 * Records comments (remarks) in batch files or scripts.
 *
 * <p>Usage: - rem This is a comment : Add a comment
 */
public class RemCommand implements Command {

  @Override
  public void execute(String[] args, ShellContext context) throws IOException {
    // REM command does nothing - it's just for comments
    // Silently ignore the comment
  }

  @Override
  public String description() {
    return "Records comments (remarks) in batch files or scripts.";
  }

  @Override
  public String usage() {
    return "rem [comment]";
  }
}
