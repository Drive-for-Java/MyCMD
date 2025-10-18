package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

/**
 * Displays how long the shell has been running since startup.
 * 
 * This command shows the uptime of the MyCMD shell session, displaying
 * the time elapsed since the shell was started. The uptime is calculated
 * from the shell start time stored in the ShellContext.
 * 
 * Usage:
 * - uptime : Display shell uptime in hours, minutes, and seconds
 * 
 * The output format shows the uptime as "Up since Xh Ym Zs" where:
 * - X is hours
 * - Y is minutes  
 * - Z is seconds
 * 
 * This is useful for monitoring how long a shell session has been active.
 */
public class UptimeCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        long uptimeMillis = System.currentTimeMillis() - context.getStartTime();
        
        long totalSeconds = uptimeMillis / 1000;
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        
        System.out.printf("Up since %dh %dm %ds%n", hours, minutes, seconds);
    }

    @Override
    public String description() {
        return "Display how long the shell has been running since startup.";
    }

    @Override
    public String usage() {
        return "uptime";
    }
}
