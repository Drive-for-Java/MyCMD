package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.IOException;

/**
 * Creates, changes, or deletes the volume label of a disk.
 *
 * <p>Usage: - label : Display current drive label - label [drive:] : Change label for specified
 * drive
 */
public class LabelCommand implements Command {

    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        System.out.println("LABEL - Creates, changes, or deletes the volume label of a disk.");
        System.out.println("\nNote: This is a read-only simulation.");
        System.out.println("Actual volume label modification requires system-level permissions.");

        if (args.length == 0) {
            // Show current drive label
            String currentPath = context.getCurrentDir().getAbsolutePath();
            String driveLetter = currentPath.substring(0, Math.min(2, currentPath.length()));
            System.out.println("\nVolume in drive " + driveLetter + " has no label.");
            System.out.println("Volume label (11 characters, ENTER for none)? ");
        } else {
            System.out.println("\nSpecified drive: " + args[0]);
            System.out.println("Volume label change requires administrator privileges.");
        }
    }

    @Override
    public String description() {
        return "Creates, changes, or deletes the volume label of a disk.";
    }

    @Override
    public String usage() {
        return "label [drive:][label]";
    }
}
