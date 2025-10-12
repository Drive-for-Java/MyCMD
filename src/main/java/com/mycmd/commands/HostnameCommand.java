package com.mycmd.commands;

import java.net.InetAddress;

import com.mycmd.Command;
import com.mycmd.ShellContext;

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
}
