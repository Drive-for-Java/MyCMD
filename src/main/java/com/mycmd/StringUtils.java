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
        return closest;
    }
}
