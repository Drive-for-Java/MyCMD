package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

/**
 * Prints the current working directory.
 *
 * <p>This command displays the absolute path of the current working directory stored in the shell
 * context. It's equivalent to the Unix/Linux 'pwd' command and provides the same functionality as
 * 'cd' without arguments.
 *
 * <p>Usage: - pwd : Print the current working directory path
 *
 * <p>The command always prints the absolute path of the current directory, making it useful for
 * scripts and when you need to know your exact location in the file system.
 */
public class PwdCommand implements Command {
  @Override
  public void execute(String[] args, ShellContext context) {
    System.out.println(context.getCurrentDir().getAbsolutePath());
  }

  @Override
  public String description() {
    return "Print the current working directory path.";
  }

  @Override
  public String usage() {
    return "pwd";
  }
}
