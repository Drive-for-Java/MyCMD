package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Interactive history search command (similar to Ctrl+R in bash). Usage: isearch
 *
 * <p>Provides an interactive prompt where users can: - Type to filter history in real-time -
 * Navigate through matches with numbers - Execute a selected command
 */
public class InteractiveSearchCommand implements Command {

  @Override
  public void execute(String[] args, ShellContext context) {
    List<String> history = context.getHistory();

    if (history.isEmpty()) {
      System.out.println("No command history available.");
      return;
    }

    // ✅ Use shared scanner from ShellContext instead of creating a new one
    Scanner scanner = context.getScanner();

    System.out.println("=== Interactive History Search ===");
    System.out.println("Type to search, 'q' to quit");
    System.out.println();

    while (true) {
      System.out.print("search> ");
      String searchTerm = scanner.nextLine().trim();

      if (searchTerm.equalsIgnoreCase("q") || searchTerm.equalsIgnoreCase("quit")) {
        System.out.println("Search cancelled.");
        break;
      }

      if (searchTerm.isEmpty()) {
        System.out.println("Enter a search term or 'q' to quit.");
        continue;
      }

      // Search and display results
      List<String> matches = searchHistory(history, searchTerm);

      if (matches.isEmpty()) {
        System.out.println("No matches found for: '" + searchTerm + "'");
        System.out.println();
        continue;
      }

      // Display matches with numbers
      System.out.println("\nMatches for '" + searchTerm + "':");
      for (int i = 0; i < Math.min(matches.size(), 10); i++) {
        System.out.println("  " + (i + 1) + ". " + matches.get(i));
      }

      if (matches.size() > 10) {
        System.out.println("  ... and " + (matches.size() - 10) + " more");
      }

      // Ask user to select or refine
      System.out.println();
      System.out.print(
          "Select number to copy (1-"
              + Math.min(matches.size(), 10)
              + "), or press Enter to search again: ");
      String selection = scanner.nextLine().trim();

      if (selection.isEmpty()) {
        continue;
      }

      try {
        int index = Integer.parseInt(selection) - 1;
        if (index >= 0 && index < Math.min(matches.size(), 10)) {
          String selectedCommand = matches.get(index);
          System.out.println("\nSelected command: " + selectedCommand);
          System.out.println("(You can now run this command by typing it at the prompt)");
          break;
        } else {
          System.out.println("Invalid selection. Try again.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Enter a number or press Enter.");
      }

      System.out.println();
    }
  }

  /**
   * Search history for commands containing the search term. Returns matches in reverse order (most
   * recent first).
   */
  private List<String> searchHistory(List<String> history, String searchTerm) {
    String lowerSearch = searchTerm.toLowerCase();

    // Search in reverse order (most recent first)
    return history.stream()
        .filter(cmd -> cmd.toLowerCase().contains(lowerSearch))
        .collect(
            Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                  java.util.Collections.reverse(list);
                  return list;
                }));
  }

  @Override
  public String description() {
    return "Interactive history search (like Ctrl+R)";
  }

  @Override
  public String usage() {
    return "isearch\n"
        + "  Opens an interactive prompt to search command history.\n"
        + "  Type your search term and select from matching commands.";
  }
}
