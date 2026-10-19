package net.twcbmod.highlighter;

import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;
import net.twcbmod.translator.CommandTranslationDictionary;
import net.twcbmod.translator.RussianRegistryDictionary;
import net.twcbmod.translator.WordToNumberParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CommandSyntaxHighlighter {

    // Command Category RGB Colors
    public static final TextColor COLOR_WORLD_BLOCKS   = TextColor.fromRgb(0x4ADE80); // Emerald Green
    public static final TextColor COLOR_PLAYERS_ENTITIES = TextColor.fromRgb(0xFBBF24); // Golden Orange
    public static final TextColor COLOR_LOGIC_AUTOMATION = TextColor.fromRgb(0xC084FC); // Bright Purple
    public static final TextColor COLOR_COMBAT_DAMAGE   = TextColor.fromRgb(0xF87171); // Coral Red
    public static final TextColor COLOR_VISUALS_AUDIO    = TextColor.fromRgb(0x38BDF8); // Sky Blue
    public static final TextColor COLOR_SERVER_ADMIN     = TextColor.fromRgb(0x94A3B8); // Steel Slate

    // Argument Token RGB Colors
    public static final TextColor COLOR_TARGET_SELECTOR = TextColor.fromRgb(0x2DD4BF); // Turquoise
    public static final TextColor COLOR_COORDINATES_NUM = TextColor.fromRgb(0x86EFAC); // Mint Green (0x86EFAC)
    public static final TextColor COLOR_REGISTRY_NAME  = TextColor.fromRgb(0xFEF08A); // Soft Yellow
    public static final TextColor COLOR_SUBCOMMAND     = TextColor.fromRgb(0xF472B6); // Pink-Magenta
    public static final TextColor COLOR_QUOTED_STRING  = TextColor.fromRgb(0xCBD5E1); // Light Gray Italic
    public static final TextColor COLOR_UNKNOWN_ROOT    = TextColor.fromRgb(0xEF4444); // Bright Red Underlined

    public static TextColor getRootCommandColor(String commandName) {
        if (commandName == null) return COLOR_UNKNOWN_ROOT;
        String vanilla = CommandTranslationDictionary.getRootCommand(commandName);
        if (vanilla == null) {
            vanilla = commandName.toLowerCase(Locale.ROOT);
        }

        return switch (vanilla) {
            case "fill", "setblock", "clone", "fillbiome", "place", "setworldspawn", "weather", "time", "worldborder", "forceload", "seed", "tick" -> COLOR_WORLD_BLOCKS;
            case "teleport", "tp", "summon", "give", "effect", "gamemode", "defaultgamemode", "enchant", "experience", "xp", "attribute", "ride", "spectate", "spawnpoint", "spreadplayers", "tag", "team", "advancement", "recipe", "item", "loot" -> COLOR_PLAYERS_ENTITIES;
            case "execute", "scoreboard", "data", "function", "return", "schedule", "trigger", "random", "datapack" -> COLOR_LOGIC_AUTOMATION;
            case "kill", "damage", "clear" -> COLOR_COMBAT_DAMAGE;
            case "particle", "playsound", "stopsound", "title", "tellraw", "say", "msg", "tell", "w", "teammsg", "tm", "me", "bossbar" -> COLOR_VISUALS_AUDIO;
            case "gamerule", "difficulty", "op", "deop", "kick", "ban", "ban-ip", "banlist", "pardon", "pardon-ip", "whitelist", "locate", "reload", "help", "list", "stop", "publish", "setidletimeout", "transfer", "debug", "perf", "jfr" -> COLOR_SERVER_ADMIN;
            default -> COLOR_UNKNOWN_ROOT;
        };
    }

    public static Style getStyleForToken(String token, boolean isRoot, String currentRoot) {
        if (token == null || token.isEmpty()) {
            return Style.EMPTY;
        }

        if (isRoot) {
            TextColor color = getRootCommandColor(token);
            if (color == COLOR_UNKNOWN_ROOT) {
                return Style.EMPTY.withColor(color).withUnderline(true);
            }
            return Style.EMPTY.withColor(color);
        }

        String clean = token.toLowerCase(Locale.ROOT);

        // Check if quoted string
        if ((token.startsWith("\"") && token.endsWith("\"")) || (token.startsWith("'") && token.endsWith("'"))) {
            return Style.EMPTY.withColor(COLOR_QUOTED_STRING).withItalic(true);
        }

        // Target selectors & contextual target words
        if (token.startsWith("@") || CommandTranslationDictionary.getContextualTarget(clean) != null) {
            return Style.EMPTY.withColor(COLOR_TARGET_SELECTOR);
        }

        // Coordinates & numbers
        if (clean.startsWith("~") || clean.startsWith("^") || clean.matches("-?\\d+(\\.\\d+)?") || "минус".equals(clean) || WordToNumberParser.isNumberWord(clean)) {
            return Style.EMPTY.withColor(COLOR_COORDINATES_NUM);
        }

        // Subcommand keywords
        if (CommandTranslationDictionary.getSubcommandKeyword(clean) != null) {
            return Style.EMPTY.withColor(COLOR_SUBCOMMAND);
        }

        // Items, blocks, entities, status effects
        if (RussianRegistryDictionary.getVanillaId(clean) != null || clean.startsWith("minecraft:")) {
            return Style.EMPTY.withColor(COLOR_REGISTRY_NAME);
        }

        return Style.EMPTY;
    }

    public static OrderedText highlight(String input, int cursor) {
        if (input == null || input.isEmpty()) {
            return OrderedText.empty();
        }

        List<HighlighterToken> tokens = tokenizeForHighlighting(input);
        return visitor -> {
            for (HighlighterToken tok : tokens) {
                for (int i = 0; i < tok.text.length(); i++) {
                    char c = tok.text.charAt(i);
                    if (!visitor.accept(i, tok.style, c)) {
                        return false;
                    }
                }
            }
            return true;
        };
    }

    public static class HighlighterToken {
        public final String text;
        public final Style style;

        public HighlighterToken(String text, Style style) {
            this.text = text;
            this.style = style;
        }
    }

    public static List<HighlighterToken> tokenizeForHighlighting(String input) {
        List<HighlighterToken> result = new ArrayList<>();
        if (input == null || input.isEmpty()) return result;

        boolean hasSlash = input.startsWith("/");
        String textToProcess = hasSlash ? input.substring(1) : input;

        if (hasSlash) {
            result.add(new HighlighterToken("/", Style.EMPTY.withColor(COLOR_SERVER_ADMIN)));
        }

        String[] parts = textToProcess.split("(?<=\\s)|(?=\\s)");
        boolean expectingRoot = true;
        String currentRoot = null;

        for (String part : parts) {
            if (part.trim().isEmpty()) {
                result.add(new HighlighterToken(part, Style.EMPTY));
                continue;
            }

            if (expectingRoot) {
                Style style = getStyleForToken(part, true, null);
                result.add(new HighlighterToken(part, style));
                currentRoot = part;
                expectingRoot = false;
                continue;
            }

            String clean = part.trim().toLowerCase(Locale.ROOT);
            if ("запустить".equals(clean) || "run".equals(clean)) {
                Style style = getStyleForToken(part, false, currentRoot);
                result.add(new HighlighterToken(part, style));
                expectingRoot = true;
                currentRoot = null;
                continue;
            }

            Style style = getStyleForToken(part, false, currentRoot);
            result.add(new HighlighterToken(part, style));
        }

        return result;
    }
}
