package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class SysteminfoCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        NumberFormat nf = NumberFormat.getInstance();
        Runtime rt = Runtime.getRuntime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getDefault());

        String hostname;
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            hostname = System.getenv("COMPUTERNAME");
            if (hostname == null) hostname = System.getenv("HOSTNAME");
            if (hostname == null) hostname = "Unknown";
        }

        long uptime = ManagementFactory.getRuntimeMXBean().getUptime();
        long bootTimeMillis = System.currentTimeMillis() - uptime;
        long maxMem = rt.maxMemory();
        long totalMem = rt.totalMemory();
        long freeMem = rt.freeMemory();
        long usedMem = totalMem - freeMem;

        System.out.println();
        System.out.println("Host Name:             " + hostname);
        System.out.println("OS Name:               " + System.getProperty("os.name"));
        System.out.println("OS Version:            " + System.getProperty("os.version"));
        System.out.println("Architecture:          " + System.getProperty("os.arch"));
        System.out.println("User Name:             " + System.getProperty("user.name"));
        System.out.println("Java Vendor:           " + System.getProperty("java.vendor"));
        System.out.println("Java VM:               " + System.getProperty("java.vm.name"));
        System.out.println("Available Processors:  " + osBean.getAvailableProcessors());
        System.out.println("JVM Boot Time:         " + sdf.format(new Date(bootTimeMillis)));
        System.out.println("User Language:         " + System.getProperty("user.language"));
        System.out.println("User Country:          " + System.getProperty("user.country"));
        System.out.println("Home Directory:        " + System.getProperty("user.home"));
        System.out.println();
        System.out.println("JVM Memory (Heap):");
        System.out.println("  Total: " + nf.format(totalMem / (1024 * 1024)) + " MB");
        System.out.println("  Used:  " + nf.format(usedMem / (1024 * 1024)) + " MB");
        System.out.println("  Free:  " + nf.format(freeMem / (1024 * 1024)) + " MB");
        System.out.println(
                "  Max:   "
                        + (maxMem == Long.MAX_VALUE
                                ? "No Limit"
                                : nf.format(maxMem / (1024 * 1024)) + " MB"));
        System.out.println();
    }

    @Override
    public String description() {
        return "Displays system and JVM information.";
    }

    @Override
    public String usage() {
        return "systeminfo";
    }
}
