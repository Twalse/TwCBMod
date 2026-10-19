package net.twcbmod.mixin.client;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.command.CommandSource;
import net.minecraft.text.OrderedText;
import net.twcbmod.autocomplete.FuzzyCommandMatcher;
import net.twcbmod.highlighter.CommandSyntaxHighlighter;
import net.twcbmod.translator.CommandPreprocessor;
import net.twcbmod.translator.CommandTranslationDictionary;
import net.twcbmod.translator.RussianRegistryDictionary;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

@Mixin(ChatInputSuggestor.class)
public abstract class ChatInputSuggestorMixin {

    @Shadow @Final private TextFieldWidget textField;
    @Shadow private ParseResults<CommandSource> parse;

    @Inject(method = "refresh", at = @At("TAIL"))
    private void onRefreshTail(CallbackInfo ci) {
        String text = textField.getText();
        if (text == null || text.isEmpty()) return;

        // Custom Russian syntax highlighting
        // When text contains Russian commands or number words, use custom syntax highlighter
        boolean containsRussian = text.chars().anyMatch(ch -> Character.UnicodeBlock.of(ch) == Character.UnicodeBlock.CYRILLIC);
        if (containsRussian) {
            this.textField.setRenderTextProvider((input, cursor) -> CommandSyntaxHighlighter.highlight(input, cursor));
        }
    }

    @Inject(method = "showCommandSuggestions", at = @At("HEAD"), cancellable = true)
    private void onShowCommandSuggestions(CallbackInfo ci) {
        String text = textField.getText();
        if (text == null || text.isEmpty()) return;

        boolean containsRussian = text.chars().anyMatch(ch -> Character.UnicodeBlock.of(ch) == Character.UnicodeBlock.CYRILLIC);
        if (!containsRussian) return;

        // Provide Russian suggestions
        String currentText = text.substring(0, textField.getCursor());
        String[] parts = currentText.split(" ", -1);

        SuggestionsBuilder builder = new SuggestionsBuilder(currentText, currentText.lastIndexOf(' ') + 1);
        String lastPart = parts[parts.length - 1].toLowerCase(Locale.ROOT);

        if (parts.length == 1) {
            // Suggest root commands
            List<String> matches = FuzzyCommandMatcher.findMatches(lastPart);
            for (String match : matches) {
                builder.suggest(match.startsWith("/") ? match : "/" + match);
            }
        } else {
            String root = parts[0].replace("/", "").toLowerCase(Locale.ROOT);
            String vanillaRoot = CommandTranslationDictionary.getRootCommand(root);

            if (vanillaRoot != null || root.isEmpty()) {
                // Target position check
                if (parts.length == 2 && ("teleport".equals(vanillaRoot) || "give".equals(vanillaRoot) || "kill".equals(vanillaRoot) || "gamemode".equals(vanillaRoot) || "effect".equals(vanillaRoot))) {
                    for (String sel : CommandTranslationDictionary.getTargetSelectorsMap().keySet()) {
                        if (sel.startsWith(lastPart)) builder.suggest(sel);
                    }
                    for (String ctx : CommandTranslationDictionary.getContextualTargetsMap().keySet()) {
                        if (ctx.startsWith(lastPart)) builder.suggest(ctx);
                    }
                } else {
                    // Registry item / block / entity suggestions
                    for (String ruName : RussianRegistryDictionary.getRegistryMap().keySet()) {
                        if (ruName.startsWith(lastPart)) {
                            builder.suggest(ruName);
                        }
                    }
                }
            }
        }
    }
}
