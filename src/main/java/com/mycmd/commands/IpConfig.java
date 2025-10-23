package com.mycmd.commands;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import com.mycmd.Command;
import com.mycmd.ShellContext;

/**
 * Implements the "ipconfig" command for MyCMD.
 */
public class IpConfig implements Command {

    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                System.out.println("Interface: " + ni.getDisplayName());

                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    System.out.println("  IP Address: " + addr.getHostAddress());
                }

                System.out.println(); // blank line between interfaces
            }
        } catch (SocketException e) {
            throw new IOException("Failed to get network interfaces", e);
        }
    }

    @Override
    public String description() {
        return "Displays all network interfaces and their IP addresses";
    }

    @Override
    public String usage() {
        return "ipconfig";
    }
}
