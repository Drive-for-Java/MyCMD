package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * TasklistCommand - displays information about running processes. Usage: tasklist
 *
 * <p>Notes: - Shows Java process information and system processes if available - Limited to what
 * Java can access without platform-specific tools
 */
public class TasklistCommand implements Command {

  @Override
  public void execute(String[] args, ShellContext context) throws IOException {
    System.out.println();
    System.out.printf("%-40s %10s %15s%n", "Image Name", "PID", "Memory Usage");
    System.out.println("=".repeat(70));

    // Get current Java process information
    RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
    String jvmName = runtimeMXBean.getName();
    String[] parts = jvmName.split("@");
    String pid = parts.length > 0 ? parts[0] : "Unknown";

    // Get memory info
    Runtime runtime = Runtime.getRuntime();
    long usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024); // MB

    System.out.printf("%-40s %10s %12s MB%n", "java.exe", pid, usedMemory);

    // Try to get system processes using ProcessHandle (Java 9+)
    try {
      ProcessHandle.allProcesses()
          .limit(20) // Limit to first 20 processes
          .forEach(
              process -> {
                ProcessHandle.Info info = process.info();
                long processPid = process.pid();
                String command = info.command().orElse("Unknown");

                // Extract just the executable name from full path
                String execName = command;
                if (command.contains("/") || command.contains("\\")) {
                  int lastSlash = Math.max(command.lastIndexOf('/'), command.lastIndexOf('\\'));
                  execName = command.substring(lastSlash + 1);
                }

                // Truncate long names
                if (execName.length() > 40) {
                  execName = execName.substring(0, 37) + "...";
                }

                System.out.printf("%-40s %10d %15s%n", execName, processPid, "N/A");
              });
    } catch (Exception e) {
      // ProcessHandle not available or error accessing processes
      System.out.println("\n[Additional process information not available]");
    }

    System.out.println();
  }

  @Override
  public String description() {
    return "Display a list of currently running processes.";
  }

  @Override
  public String usage() {
    return "tasklist";
  }
}
