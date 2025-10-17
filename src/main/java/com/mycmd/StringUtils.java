package com.mycmd;

import java.util.List;

public class StringUtils {
    // Compute Levenshtein distance between two strings
    public static int levenshtein(String a, String b) {
        int[] costs = new int[b.length() + 1];
        for (int j = 0; j < costs.length; j++) {
            costs[j] = j;
        }
        for (int i = 1; i <= a.length(); i++) {
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }

    // Find the closest string from a list
    public static String findClosest(String input, List<String> candidates) {
        String closest = null;
        int minDist = Integer.MAX_VALUE;
        for (String candidate : candidates) {
            int dist = levenshtein(input, candidate);
            if (dist < minDist) {
                minDist = dist;
                closest = candidate;
            }
        }
        // Only return a suggestion when the distance is small enough to be useful.
        // A threshold of 2 works well for short typos (e.g. "di" -> "dir"),
        // and we also allow a slightly larger threshold for longer candidates.
        if (closest == null) return null;
        int threshold = 2;
        int len = Math.max(input.length(), closest.length());
        // allow up to ~25% of the length as distance for long words (min 2)
        int adaptive = Math.max(threshold, len / 4);
        if (minDist <= adaptive) {
            return closest;
        }
        return null;
    }
}