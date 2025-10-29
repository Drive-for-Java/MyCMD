package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.File;
import java.io.IOException;

/**
 * Displays the volume label and serial number of a disk.
 *
 * <p>Usage: - vol : Display current drive volume info - vol C: : Display volume info for drive C:
 */
public class VolCommand implements Command {

    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        File drive;

        if (args.length == 0) {
            drive = context.getCurrentDir();
        } else {
            String drivePath = args[0];
            if (!drivePath.endsWith("\\") && !drivePath.endsWith("/")) {
                drivePath += "\\";
            }
            drive = new File(drivePath);
        }

        if (!drive.exists()) {
            System.out.println("The system cannot find the drive specified.");
            return;
        }

        // Get drive letter
        String path = drive.getAbsolutePath();
        String driveLetter = path.substring(0, Math.min(2, path.length()));

        System.out.println(" Volume in drive " + driveLetter + " has no label.");
        System.out.println(" Volume Serial Number is " + getSerialNumber(drive));
    }

    private String getSerialNumber(File drive) {
        // Generate a pseudo-serial based on drive properties
        long totalSpace = drive.getTotalSpace();
        long freeSpace = drive.getFreeSpace();
        long combined = totalSpace ^ freeSpace;
        return String.format(
                "%04X-%04X", (int) ((combined >>> 16) & 0xFFFF), (int) (combined & 0xFFFF));
    }

    @Override
    public String description() {
        return "Display the disk volume label and serial number.";
    }

    @Override
    public String usage() {
        return "vol [drive:]";
    }
}
