package net.twcbmod.autocomplete;

import net.twcbmod.translator.CommandTranslationDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FuzzyCommandMatcher {

    public static List<String> findMatches(String input) {
        List<String> matches = new ArrayList<>();
        if (input == null || input.isEmpty()) return matches;

        String clean = input.toLowerCase(Locale.ROOT);
        if (clean.startsWith("/")) clean = clean.substring(1);

        // 1. Prefix matches
        for (String alias : CommandTranslationDictionary.getRootCommandsMap().keySet()) {
            if (alias.startsWith(clean)) {
                matches.add(alias);
            }
        }

        if (!matches.isEmpty()) {
            return matches;
        }

        // 2. Fuzzy match using Levenshtein distance
        int bestDistance = Integer.MAX_VALUE;
        String bestMatch = null;

        for (String alias : CommandTranslationDictionary.getRootCommandsMap().keySet()) {
            int maxThreshold = alias.length() >= 8 ? 3 : (alias.length() >= 4 ? 2 : 1);
            int dist = computeLevenshteinDistance(clean, alias);
            if (dist <= maxThreshold && dist < bestDistance) {
                bestDistance = dist;
                bestMatch = alias;
            }
        }

        if (bestMatch != null) {
            matches.add(bestMatch);
        }

        return matches;
    }

    public static int computeLevenshteinDistance(String s1, String s2) {
        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    costs[j] = j;
                } else if (j > 0) {
                    int newValue = costs[j - 1];
                    if (s1.charAt(i - 1) != s2.charAt(j - 1)) {
                        newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
                    }
                    costs[j - 1] = lastValue;
                    lastValue = newValue;
                }
            }
            if (i > 0) costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }
}
