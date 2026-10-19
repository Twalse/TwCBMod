package net.twcbmod.translator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandPreprocessor {

    private enum TokenType {
        TEXT,
        DOUBLE_QUOTED_STRING,
        SINGLE_QUOTED_STRING
    }

    private static class Token {
        final TokenType type;
        final String content;

        Token(TokenType type, String content) {
            this.type = type;
            this.content = content;
        }
    }

    /**
     * Translates a command string (with or without leading slash) from Russian into vanilla English syntax.
     */
    public static String translateCommand(String command) {
        if (command == null || command.isEmpty()) {
            return command;
        }

        boolean hasSlash = command.startsWith("/");
        String raw = hasSlash ? command.substring(1) : command;

        List<Token> tokens = tokenize(raw);
        StringBuilder result = new StringBuilder();
        if (hasSlash) {
            result.append("/");
        }

        // Track context across text tokens (e.g. whether we are expecting a root command or after 'run')
        boolean isFirstWordInCommand = true;
        boolean afterRun = false;

        for (Token token : tokens) {
            if (token.type != TokenType.TEXT) {
                result.append(token.content);
                continue;
            }

            TextProcessResult tpr = processTextSegment(token.content, isFirstWordInCommand, afterRun);
            result.append(tpr.translatedText);
            isFirstWordInCommand = tpr.nextIsFirstWordInCommand;
            afterRun = tpr.nextAfterRun;
        }

        return result.toString();
    }

    private static List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inDoubleQuote = false;
        boolean inSingleQuote = false;
        boolean escaped = false;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (escaped) {
                current.append(c);
                escaped = false;
                continue;
            }

            if (c == '\\') {
                current.append(c);
                escaped = true;
                continue;
            }

            if (c == '"' && !inSingleQuote) {
                if (inDoubleQuote) {
                    current.append(c);
                    tokens.add(new Token(TokenType.DOUBLE_QUOTED_STRING, current.toString()));
                    current.setLength(0);
                    inDoubleQuote = false;
                } else {
                    if (current.length() > 0) {
                        tokens.add(new Token(TokenType.TEXT, current.toString()));
                        current.setLength(0);
                    }
                    current.append(c);
                    inDoubleQuote = true;
                }
                continue;
            }

            if (c == '\'' && !inDoubleQuote) {
                if (inSingleQuote) {
                    current.append(c);
                    tokens.add(new Token(TokenType.SINGLE_QUOTED_STRING, current.toString()));
                    current.setLength(0);
                    inSingleQuote = false;
                } else {
                    if (current.length() > 0) {
                        tokens.add(new Token(TokenType.TEXT, current.toString()));
                        current.setLength(0);
                    }
                    current.append(c);
                    inSingleQuote = true;
                }
                continue;
            }

            current.append(c);
        }

        if (current.length() > 0) {
            TokenType type = inDoubleQuote ? TokenType.DOUBLE_QUOTED_STRING :
                    (inSingleQuote ? TokenType.SINGLE_QUOTED_STRING : TokenType.TEXT);
            tokens.add(new Token(type, current.toString()));
        }

        return tokens;
    }

    private static class TextProcessResult {
        final String translatedText;
        final boolean nextIsFirstWordInCommand;
        final boolean nextAfterRun;

        TextProcessResult(String translatedText, boolean nextIsFirstWordInCommand, boolean nextAfterRun) {
            this.translatedText = translatedText;
            this.nextIsFirstWordInCommand = nextIsFirstWordInCommand;
            this.nextAfterRun = nextAfterRun;
        }
    }

    private static TextProcessResult processTextSegment(String text, boolean isFirstWordInCommand, boolean afterRun) {
        // Step 1: Translate Target Selectors (@все, @игрок, etc.) and their selector brackets [...]
        String textWithSelectors = translateSelectors(text);

        // Step 2: Parse and replace Russian number words ("один", "ноль", "сто", "~минус пять", etc.)
        String textWithNumbers = WordToNumberParser.parseNumbersInString(textWithSelectors);

        // Step 3: Translate command and subcommand words
        return translateCommandWords(textWithNumbers, isFirstWordInCommand, afterRun);
    }

    /**
     * Translates Russian target selectors like @все, @игрок[тип=корова] into @a, @p[type=cow], etc.
     */
    private static String translateSelectors(String text) {
        // Regex to match target selectors and optional arguments [...]
        Pattern selectorPattern = Pattern.compile("(@[a-zA-Zа-яА-ЯёЁ]+)(\\[[^\\]]*\\])?");
        Matcher matcher = selectorPattern.matcher(text);

        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            String selToken = matcher.group(1);
            String bracketPart = matcher.group(2);

            String translatedSel = CommandTranslationDictionary.getTargetSelector(selToken);
            if (translatedSel == null) {
                translatedSel = selToken;
            }

            String translatedBrackets = "";
            if (bracketPart != null) {
                translatedBrackets = translateSelectorBrackets(bracketPart);
            }

            matcher.appendReplacement(sb, Matcher.quoteReplacement(translatedSel + translatedBrackets));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private static String translateSelectorBrackets(String brackets) {
        // Remove [ and ] for parsing content
        String content = brackets.substring(1, brackets.length() - 1);
        if (content.trim().isEmpty()) {
            return brackets;
        }

        String[] args = content.split(",");
        List<String> translatedArgs = new ArrayList<>();

        for (String arg : args) {
            String[] kv = arg.split("=", 2);
            if (kv.length == 2) {
                String key = kv[0].trim();
                String val = kv[1].trim();

                String translatedKey = CommandTranslationDictionary.getSelectorArgKey(key);
                if (translatedKey == null) {
                    translatedKey = key;
                }

                // Parse number words inside selector argument value if any
                String valWithNum = WordToNumberParser.parseNumbersInString(val);

                String translatedVal = CommandTranslationDictionary.getSelectorArgValue(valWithNum);
                if (translatedVal == null) {
                    translatedVal = valWithNum;
                }

                translatedArgs.add(translatedKey + "=" + translatedVal);
            } else {
                translatedArgs.add(arg);
            }
        }

        return "[" + String.join(",", translatedArgs) + "]";
    }

    private static TextProcessResult translateCommandWords(String text, boolean initialFirstWord, boolean initialAfterRun) {
        String[] tokens = text.split("(?<=\\s)|(?=\\s)");
        StringBuilder sb = new StringBuilder();

        boolean firstWord = initialFirstWord;
        boolean afterRun = initialAfterRun;

        for (String token : tokens) {
            if (token.trim().isEmpty()) {
                sb.append(token);
                continue;
            }

            String clean = token.trim();

            if (firstWord || afterRun) {
                String rootTrans = CommandTranslationDictionary.getRootCommand(clean);
                if (rootTrans != null) {
                    sb.append(rootTrans);
                    afterRun = false;
                    firstWord = false;
                    continue;
                }
            }

            // Check subcommand keyword
            String subTrans = CommandTranslationDictionary.getSubcommandKeyword(clean);
            if (subTrans != null) {
                sb.append(subTrans);
                if ("run".equals(subTrans)) {
                    afterRun = true;
                }
                firstWord = false;
                continue;
            }

            // Otherwise, keep as is
            sb.append(token);
            firstWord = false;
        }

        return new TextProcessResult(sb.toString(), firstWord, afterRun);
    }
}
