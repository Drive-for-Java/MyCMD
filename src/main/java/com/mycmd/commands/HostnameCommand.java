package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.net.InetAddress;

/**
 * Displays the hostname of the current computer.
 *
 * <p>This command attempts to retrieve the system hostname using two methods: first by checking the
 * COMPUTERNAME environment variable (Windows-specific), and if that fails, by using
 * InetAddress.getLocalHost().getHostName(). If both methods fail, it displays "Unknown Host".
 *
 * <p>Usage: hostname
 *
 * <p>Note: This command works best on Windows systems where the COMPUTERNAME environment variable
 * is typically set.
 */
public class HostnameCommand implements Command {
  @Override
  public void execute(String[] args, ShellContext context) {
    String hostname = System.getenv("COMPUTERNAME");
    if (hostname == null) {
      try {
        hostname = InetAddress.getLocalHost().getHostName();
      } catch (Exception e) {
        hostname = "Unknown Host";
      }
    }
    System.out.println(hostname);
  }

  @Override
  public String description() {
    return "Display the hostname of the current computer.";
  }

  @Override
  public String usage() {
    return "hostname";
  }
}
