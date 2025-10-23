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
    public void execute(String[] args, ShellContext context) throws IOException {
        Instant startTime = context.getStartTime();
        Instant now = Instant.now();
        
        // Calculate duration between start time and now
        Duration uptime = Duration.between(startTime, now);
        
        long seconds = uptime.getSeconds();
        long days = seconds / 86400;
        long hours = (seconds % 86400) / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        
        System.out.print("Uptime: ");
        if (days > 0) {
            System.out.print(days + " day" + (days != 1 ? "s" : "") + ", ");
        }
        System.out.printf("%02d:%02d:%02d%n", hours, minutes, secs);
    }

    @Override
    public String description() {
        return "Display how long the shell has been running.";
    }

    @Override
    public String usage() {
        return "uptime";
    }
}