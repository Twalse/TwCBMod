package net.twcbmod.translator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordToNumberParser {

    private enum WordType {
        SIGN,
        ZERO,
        UNIT,
        TEEN,
        TEN,
        HUNDRED,
        SCALE
    }

    private static class NumWord {
        final long value;
        final WordType type;

        NumWord(long value, WordType type) {
            this.value = value;
            this.type = type;
        }
    }

    private static final Map<String, NumWord> WORD_MAP = new HashMap<>();

    static {
        // Sign
        WORD_MAP.put("минус", new NumWord(0, WordType.SIGN));

        // Zero
        WORD_MAP.put("ноль", new NumWord(0, WordType.ZERO));
        WORD_MAP.put("нуль", new NumWord(0, WordType.ZERO));

        // Units
        WORD_MAP.put("один", new NumWord(1, WordType.UNIT));
        WORD_MAP.put("одна", new NumWord(1, WordType.UNIT));
        WORD_MAP.put("одно", new NumWord(1, WordType.UNIT));
        WORD_MAP.put("два", new NumWord(2, WordType.UNIT));
        WORD_MAP.put("две", new NumWord(2, WordType.UNIT));
        WORD_MAP.put("три", new NumWord(3, WordType.UNIT));
        WORD_MAP.put("четыре", new NumWord(4, WordType.UNIT));
        WORD_MAP.put("пять", new NumWord(5, WordType.UNIT));
        WORD_MAP.put("шесть", new NumWord(6, WordType.UNIT));
        WORD_MAP.put("семь", new NumWord(7, WordType.UNIT));
        WORD_MAP.put("восемь", new NumWord(8, WordType.UNIT));
        WORD_MAP.put("девять", new NumWord(9, WordType.UNIT));

        // Teens & 10
        WORD_MAP.put("десять", new NumWord(10, WordType.TEEN));
        WORD_MAP.put("одиннадцать", new NumWord(11, WordType.TEEN));
        WORD_MAP.put("двенадцать", new NumWord(12, WordType.TEEN));
        WORD_MAP.put("тринадцать", new NumWord(13, WordType.TEEN));
        WORD_MAP.put("четырнадцать", new NumWord(14, WordType.TEEN));
        WORD_MAP.put("пятнадцать", new NumWord(15, WordType.TEEN));
        WORD_MAP.put("шестнадцать", new NumWord(16, WordType.TEEN));
        WORD_MAP.put("семнадцать", new NumWord(17, WordType.TEEN));
        WORD_MAP.put("восемнадцать", new NumWord(18, WordType.TEEN));
        WORD_MAP.put("девятнадцать", new NumWord(19, WordType.TEEN));

        // Tens
        WORD_MAP.put("двадцать", new NumWord(20, WordType.TEN));
        WORD_MAP.put("тридцать", new NumWord(30, WordType.TEN));
        WORD_MAP.put("сорок", new NumWord(40, WordType.TEN));
        WORD_MAP.put("пятьдесят", new NumWord(50, WordType.TEN));
        WORD_MAP.put("шестьдесят", new NumWord(60, WordType.TEN));
        WORD_MAP.put("семьдесят", new NumWord(70, WordType.TEN));
        WORD_MAP.put("восемьдесят", new NumWord(80, WordType.TEN));
        WORD_MAP.put("девяносто", new NumWord(90, WordType.TEN));

        // Hundreds
        WORD_MAP.put("сто", new NumWord(100, WordType.HUNDRED));
        WORD_MAP.put("двести", new NumWord(200, WordType.HUNDRED));
        WORD_MAP.put("триста", new NumWord(300, WordType.HUNDRED));
        WORD_MAP.put("четыреста", new NumWord(400, WordType.HUNDRED));
        WORD_MAP.put("пятьсот", new NumWord(500, WordType.HUNDRED));
        WORD_MAP.put("шестьсот", new NumWord(600, WordType.HUNDRED));
        WORD_MAP.put("семьсот", new NumWord(700, WordType.HUNDRED));
        WORD_MAP.put("восемьсот", new NumWord(800, WordType.HUNDRED));
        WORD_MAP.put("девятьсот", new NumWord(900, WordType.HUNDRED));

        // Scales
        WORD_MAP.put("тысяча", new NumWord(1000, WordType.SCALE));
        WORD_MAP.put("тысячи", new NumWord(1000, WordType.SCALE));
        WORD_MAP.put("тысяч", new NumWord(1000, WordType.SCALE));

        WORD_MAP.put("миллион", new NumWord(1000000, WordType.SCALE));
        WORD_MAP.put("миллиона", new NumWord(1000000, WordType.SCALE));
        WORD_MAP.put("миллионов", new NumWord(1000000, WordType.SCALE));

        WORD_MAP.put("миллиард", new NumWord(1000000000, WordType.SCALE));
        WORD_MAP.put("миллиарда", new NumWord(1000000000, WordType.SCALE));
        WORD_MAP.put("миллиардов", new NumWord(1000000000, WordType.SCALE));
    }

    public static boolean isNumberWord(String word) {
        if (word == null) return false;
        String clean = word.toLowerCase(Locale.ROOT);
        if (clean.startsWith("~") || clean.startsWith("^")) {
            clean = clean.substring(1);
        }
        return WORD_MAP.containsKey(clean);
    }

    /**
     * Replaces Russian number words in token lists or strings.
     * Handles standalone prefixes ~, ^ and attached prefixes ~сто, ^десять, ~минус пять, etc.
     */
    public static String parseNumbersInString(String text) {
        if (text == null || text.isEmpty()) return text;

        // Split preserving whitespace tokens or tokenizing carefully
        String[] tokens = text.split("(?<=\\s)|(?=\\s)");
        List<String> resultTokens = new ArrayList<>();

        int i = 0;
        while (i < tokens.length) {
            String token = tokens[i];
            if (token.trim().isEmpty()) {
                resultTokens.add(token);
                i++;
                continue;
            }

            // Check if token or series of tokens starts a number word
            String prefix = "";
            String curToken = token;

            if (curToken.startsWith("~") || curToken.startsWith("^")) {
                prefix = curToken.substring(0, 1);
                curToken = curToken.substring(1);
            }

            // Standalone ~ or ^ without attached word
            int advance = 0;
            if (curToken.isEmpty() && !prefix.isEmpty()) {
                resultTokens.add(token);
                i++;
                continue;
            }

            if (isStartOfNumberWord(curToken)) {
                // Try parsing a number starting at this point
                ParseResult parseRes = parseNumberFromIndex(tokens, i, advance, prefix, curToken);
                if (parseRes != null) {
                    resultTokens.add(parseRes.parsedNumber);
                    i = parseRes.nextIndex;
                    continue;
                }
            }

            resultTokens.add(token);
            i++;
        }

        StringBuilder sb = new StringBuilder();
        for (String t : resultTokens) {
            sb.append(t);
        }
        return sb.toString();
    }

    private static boolean isStartOfNumberWord(String token) {
        String clean = token.toLowerCase(Locale.ROOT);
        return WORD_MAP.containsKey(clean);
    }

    private static class ParseResult {
        final String parsedNumber;
        final int nextIndex;

        ParseResult(String parsedNumber, int nextIndex) {
            this.parsedNumber = parsedNumber;
            this.nextIndex = nextIndex;
        }
    }

    private static ParseResult parseNumberFromIndex(String[] tokens, int startIndex, int tokenOffset, String prefix, String firstWord) {
        int i = startIndex + tokenOffset;
        boolean negative = false;
        long totalResult = 0;
        long currentPeriod = 0;
        long lastScale = Long.MAX_VALUE;
        WordType lastType = null;
        int wordsConsumed = 0;

        String cleanFirst = firstWord.toLowerCase(Locale.ROOT);
        if ("минус".equals(cleanFirst)) {
            negative = true;
            wordsConsumed++;
            i++;
            // Skip whitespace to get next word
            while (i < tokens.length && tokens[i].trim().isEmpty()) {
                i++;
            }
            if (i >= tokens.length) {
                return null; // Just "минус" alone with no number words following is not a number
            }
            cleanFirst = tokens[i].toLowerCase(Locale.ROOT);
            if (!WORD_MAP.containsKey(cleanFirst) || "минус".equals(cleanFirst)) {
                return null;
            }
        }

        while (i < tokens.length) {
            String rawWord = (i == startIndex + tokenOffset) ? firstWord : tokens[i];
            if (rawWord.trim().isEmpty()) {
                // Lookahead over whitespace
                int wsCheck = i + 1;
                while (wsCheck < tokens.length && tokens[wsCheck].trim().isEmpty()) {
                    wsCheck++;
                }
                if (wsCheck >= tokens.length) {
                    break;
                }
                String nextWord = tokens[wsCheck].toLowerCase(Locale.ROOT);
                if (!WORD_MAP.containsKey(nextWord) || "минус".equals(nextWord)) {
                    break;
                }
                // Check if nextWord can legally combine with current number
                NumWord nw = WORD_MAP.get(nextWord);
                if (!canCombine(nw, lastType, currentPeriod, lastScale)) {
                    break;
                }
                i = wsCheck; // Advance i to wsCheck
                rawWord = tokens[i];
            }

            String word = rawWord.toLowerCase(Locale.ROOT);
            NumWord nw = WORD_MAP.get(word);
            if (nw == null || "минус".equals(word)) {
                break;
            }

            if (wordsConsumed > 0 && !canCombine(nw, lastType, currentPeriod, lastScale)) {
                break;
            }

            // Process word
            if (nw.type == WordType.ZERO) {
                currentPeriod = 0;
                lastType = WordType.ZERO;
            } else if (nw.type == WordType.SCALE) {
                long mult = nw.value;
                if (currentPeriod == 0) currentPeriod = 1;
                totalResult += currentPeriod * mult;
                currentPeriod = 0;
                lastScale = mult;
                lastType = WordType.SCALE;
            } else {
                currentPeriod += nw.value;
                lastType = nw.type;
            }

            wordsConsumed++;
            i++;
        }

        if (wordsConsumed == 0) {
            return null;
        }

        totalResult += currentPeriod;

        String numberStr = String.valueOf(totalResult);
        if (negative) {
            numberStr = "-" + numberStr;
        }
        String fullResult = prefix + numberStr;

        return new ParseResult(fullResult, i);
    }

    private static boolean canCombine(NumWord nextWord, WordType lastType, long currentPeriod, long lastScale) {
        if (lastType == null) return true;

        // Zero cannot combine with anything else in the same number
        if (lastType == WordType.ZERO || nextWord.type == WordType.ZERO) return false;

        // Scale rules
        if (nextWord.type == WordType.SCALE) {
            return nextWord.value < lastScale;
        }

        // Sub-thousand combination rules
        if (lastType == WordType.SCALE) {
            // After scale, any sub-thousand word can start
            return true;
        }

        // Strict ordering for sub-thousand: HUNDRED > TEN > TEEN > UNIT
        int lastRank = getRank(lastType);
        int nextRank = getRank(nextWord.type);

        return nextRank < lastRank;
    }

    private static int getRank(WordType type) {
        return switch (type) {
            case HUNDRED -> 4;
            case TEN -> 3;
            case TEEN -> 2;
            case UNIT -> 1;
            default -> 0;
        };
    }
}
