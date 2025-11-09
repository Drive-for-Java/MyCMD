package com.mycmd;

import java.util.Collection;
import java.util.Objects;

/**
 * Small utility for string-based helper methods. Provides a findClosest(...) implementation using
 * Levenshtein distance.
 */
public final class StringUtils {

    private StringUtils() {}

    /**
     * Find the closest string in candidates to the input. Returns null when no candidate is close
     * enough.
     *
     * @param input the input string
     * @param candidates candidate strings
     * @return the closest candidate or null
     */
    public static String findClosest(String input, Collection<String> candidates) {
        if (input == null || input.isEmpty() || candidates == null || candidates.isEmpty()) {
            return null;
        }

        String best = null;
        int bestDistance = Integer.MAX_VALUE;

        for (String candidate : candidates) {
            if (candidate == null || candidate.isEmpty()) continue;
            if (candidate.equalsIgnoreCase(input)) {
                // exact match ignoring case - return immediately
                return candidate;
            }
            int distance = levenshteinDistance(input.toLowerCase(), candidate.toLowerCase());
            if (distance < bestDistance) {
                bestDistance = distance;
                best = candidate;
            }
        }

        // Choose a threshold: allow suggestion only when the distance is reasonably small.
        // Here we allow suggestions when distance <= max(1, input.length()/3)
        int threshold = Math.max(1, input.length() / 3);
        if (bestDistance <= threshold) {
            return best;
        }
        return null;
    }

    // Classic iterative Levenshtein algorithm (memory O(min(m,n)))
    private static int levenshteinDistance(String a, String b) {
        if (Objects.equals(a, b)) return 0;
        if (a.length() == 0) return b.length();
        if (b.length() == 0) return a.length();

        // ensure a is the shorter
        if (a.length() > b.length()) {
            String tmp = a;
            a = b;
            b = tmp;
        }

        int[] previous = new int[a.length() + 1];
        int[] current = new int[a.length() + 1];

        for (int i = 0; i <= a.length(); i++) previous[i] = i;

        for (int j = 1; j <= b.length(); j++) {
            current[0] = j;
            char bj = b.charAt(j - 1);
            for (int i = 1; i <= a.length(); i++) {
                int cost = (a.charAt(i - 1) == bj) ? 0 : 1;
                current[i] = min3(current[i - 1] + 1, previous[i] + 1, previous[i - 1] + cost);
            }
            // swap previous and current
            int[] temporary = previous;
            previous = current;
            current = temporary;
        }
        return previous[a.length()];
    }

    private static int min3(int x, int y, int z) {
        return Math.min(x, Math.min(y, z));
    }
}
