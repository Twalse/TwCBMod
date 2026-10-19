package net.twcbmod.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractCommandBlockScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.twcbmod.gui.CommandUsageDatabase;
import net.twcbmod.highlighter.CommandSyntaxHighlighter;
import net.twcbmod.translator.CommandPreprocessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractCommandBlockScreen.class)
public abstract class AbstractCommandBlockScreenMixin {

    @Shadow protected TextFieldWidget consoleCommandTextField;

    @Inject(method = "init", at = @At("TAIL"))
    private void onInitTail(CallbackInfo ci) {
        if (this.consoleCommandTextField != null) {
            this.consoleCommandTextField.setRenderTextProvider((input, cursor) -> CommandSyntaxHighlighter.highlight(input, cursor));
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRenderTail(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (this.consoleCommandTextField == null) return;

        String input = this.consoleCommandTextField.getText();
        if (input == null || input.trim().isEmpty()) return;

        String raw = input.startsWith("/") ? input.substring(1) : input;
        String firstWord = raw.split(" ")[0];

        CommandUsageDatabase.CommandInfo info = CommandUsageDatabase.getInfo(firstWord);
        String vanillaPreview = CommandPreprocessor.translateCommand(input);

        AbstractCommandBlockScreen screen = (AbstractCommandBlockScreen) (Object) this;
        int x = screen.width / 2 - 150;
        int y = screen.height / 2 - 80;
        int width = 300;
        int height = 55;

        // Draw semi-transparent background panel
        context.fill(x, y, x + width, y + height, 0xC0000000);

        int textY = y + 5;
        net.minecraft.client.MinecraftClient client = net.minecraft.client.MinecraftClient.getInstance();
        if (client.textRenderer != null) {
            if (info != null) {
                context.drawText(client.textRenderer, Text.literal("Схема: " + info.schema).setStyle(net.minecraft.text.Style.EMPTY.withColor(0xFFD700)), x + 5, textY, 0xFFFFFF, false);
                textY += 12;
                context.drawText(client.textRenderer, Text.literal("Описание: " + info.description).setStyle(net.minecraft.text.Style.EMPTY.withColor(0xCCCCCC)), x + 5, textY, 0xFFFFFF, false);
                textY += 12;
            }

            context.drawText(client.textRenderer, Text.literal("Vanilla: /" + vanillaPreview).setStyle(net.minecraft.text.Style.EMPTY.withColor(0x4ADE80)), x + 5, textY, 0xFFFFFF, false);
        }
    }
}
