package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;

/**
 * Changes the console text and background colors using ANSI escape codes.
 *
 * <p>This command accepts a two-character hexadecimal code where the first digit represents the
 * background color and the second digit represents the text color. Valid color codes are 0-9 and
 * A-F (hexadecimal). When called without arguments, it resets colors to terminal defaults.
 *
 * <p>Usage: - color : Reset to default colors - color XY : Set background to X and text to Y (hex
 * digits 0-F)
 *
 * <p>Example: color 0A sets black background with bright green text.
 *
 * <p>Note: Background and text colors cannot be the same value.
 */
public class ColorCommand implements Command {
    @Override
    public void execute(String[] args, ShellContext context) {
        if (args.length == 1) {
            String color = args[0];

            if (color.length() != 2) {
                System.out.println("Usage: " + usage());
                return;
            }

            String background = String.valueOf(color.charAt(0));
            String text = String.valueOf(color.charAt(1));

            if (background.equals(text)) {
                return;
            }

            int bgIndex, fgIndex;
            try {
                bgIndex = Integer.parseInt(background, 16);
                fgIndex = Integer.parseInt(text, 16);
            } catch (NumberFormatException e) {
                System.out.println("Invalid color code. must use two hexadecimal digits.");
                System.out.println("Example: color 0A");
                return;
            }

            String[] ansiForeground = {
                "30", "34", "32", "36", "31", "35", "33", "37", "90", "94", "92", "96", "91", "95",
                "93", "97"
            };
            String[] ansiBackground = {
                "40", "44", "42", "46", "41", "45", "43", "47", "100", "104", "102", "106", "101",
                "105", "103", "107"
            };

            if (bgIndex >= ansiBackground.length || fgIndex >= ansiForeground.length) {
                System.out.println("Invalid color code. must use two hexadecimal digits.");
                System.out.println("Example: color 0A");
                return;
            }

            String bg = "\033[" + ansiBackground[bgIndex] + "m";
            String fg = "\033[" + ansiForeground[fgIndex] + "m";

            System.out.println(bg + fg);
        } else {
            // set default color
            System.out.println("\033[0m");
        }
    }

    @Override
    public String description() {
        return "Change console text and background colors.";
    }

    @Override
    public String usage() {
        return "color [<background><text>]\n"
                + "  <background> and <text> are hexadecimal digits (0-9, A-F).\n"
                + "  Example: color 0A sets black background with bright green text.\n"
                + "  Call without arguments to reset to default colors.";
    }
}
