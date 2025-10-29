package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.io.IOException;

/**
 * Copies text to the Windows clipboard.
 *
 * <p>Usage: - echo text | clip : Copy text to clipboard - clip < file.txt : Copy file contents to
 * clipboard
 */
public class ClipCommand implements Command {

    @Override
    public void execute(String[] args, ShellContext context) throws IOException {
        String os = System.getProperty("os.name").toLowerCase();

        if (!os.contains("win")) {
            System.out.println("CLIP command is only available on Windows systems.");
            System.out.println("On Unix-like systems, use 'xclip' or 'pbcopy' instead.");
            return;
        }

        System.out.println("Redirects command output to the Windows clipboard.");
        System.out.println("\nUsage:");
        System.out.println(
                "  DIR | CLIP              Places a copy of the DIR output on the clipboard.");
        System.out.println(
                "  CLIP < README.TXT       Places a copy of the text in README.TXT on the clipboard.");
        System.out.println("\nNote: Direct clipboard manipulation from MyCMD is limited.");
        System.out.println(
                "Use the actual Windows CLIP command with piping in CMD for full functionality.");
    }

    @Override
    public String description() {
        return "Copies text to the Windows clipboard.";
    }

    @Override
    public String usage() {
        return "command | clip";
    }
}
