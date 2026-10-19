package net.twcbmod.mixin;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.twcbmod.translator.CommandPreprocessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(CommandManager.class)
public class CommandManagerMixin {

    @ModifyVariable(method = "executeWithPrefix", at = @At("HEAD"), argsOnly = true)
    private String onExecuteWithPrefix(String command) {
        return CommandPreprocessor.translateCommand(command);
    }
}
