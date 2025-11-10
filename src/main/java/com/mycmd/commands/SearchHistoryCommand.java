package com.mycmd.commands;

import com.mycmd.Command;
import com.mycmd.ShellContext;
import java.util.List;

/**
 * Command to search through command history.
 * Usage: searchhistory [search_term]
 * 
 * If no search term is provided, shows all history.
 * If search term is provided, filters history to matching commands.
 */
public class SearchHistoryCommand implements Command {
    
    @Override
    public void execute(String[] args, ShellContext context) {
        List<String> history = context.getHistory();
        
        if (history.isEmpty()) {
            System.out.println("No command history available.");
            return;
        }
        
        // If no search term provided, show all history
        if (args.length == 0) {
            System.out.println("Command History (use 'searchhistory <term>' to filter):");
            displayHistory(history, null);
            return;
        }
        
        // Join all args as the search term (supports multi-word searches)
        String searchTerm = String.join(" ", args).toLowerCase();
        
        System.out.println("Searching history for: '" + searchTerm + "'");
        System.out.println();
        
        // Filter history
        List<String> matches = history.stream()
            .filter(cmd -> cmd.toLowerCase().contains(searchTerm))
            .toList();
        
        if (matches.isEmpty()) {
            System.out.println("No matching commands found.");
            System.out.println("Tip: Search is case-insensitive and matches partial text.");
        } else {
            displayHistory(matches, searchTerm);
            System.out.println();
            System.out.println("Found " + matches.size() + " matching command(s).");
        }
    }
    
    /**
     * Display history entries with line numbers.
     * Optionally highlights the search term if provided.
     */
    private void displayHistory(List<String> commands, String searchTerm) {
        int maxDigits = String.valueOf(commands.size()).length();
        
        for (int i = 0; i < commands.size(); i++) {
            String lineNum = String.format("%" + maxDigits + "d", i + 1);
            String command = commands.get(i);
            
            // Highlight search term if provided (simple uppercase for visibility)
            if (searchTerm != null && !searchTerm.isEmpty()) {
                command = highlightTerm(command, searchTerm);
            }
            
            System.out.println("  " + lineNum + "  " + command);
        }
    }
    
    /**
     * Simple highlighting by surrounding the search term with markers.
     * For terminal with color support, you could use ANSI codes instead.
     */
    private String highlightTerm(String text, String term) {
        // Case-insensitive highlight
        int index = text.toLowerCase().indexOf(term.toLowerCase());
        if (index == -1) {
            return text;
        }
        
        StringBuilder result = new StringBuilder();
        int lastIndex = 0;
        
        while (index >= 0) {
            result.append(text, lastIndex, index);
            result.append("[");
            result.append(text, index, index + term.length());
            result.append("]");
            
            lastIndex = index + term.length();
            index = text.toLowerCase().indexOf(term.toLowerCase(), lastIndex);
        }
        
        result.append(text.substring(lastIndex));
        return result.toString();
    }
    
    @Override
    public String description() {
        return "Search through command history";
    }
    
    @Override
    public String usage() {
        return "searchhistory [search_term]\n" +
               "  Examples:\n" +
               "    searchhistory           - Show all history\n" +
               "    searchhistory dir       - Find all 'dir' commands\n" +
               "    searchhistory cd ..     - Find all 'cd ..' commands";
    }
}
