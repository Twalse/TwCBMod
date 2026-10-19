package net.twcbmod.mixin;

import net.minecraft.world.CommandBlockExecutor;
import net.twcbmod.translator.CommandPreprocessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(CommandBlockExecutor.class)
public class CommandBlockExecutorMixin {

    @ModifyVariable(method = "setCommand", at = @At("HEAD"), argsOnly = true)
    private String onSetCommand(String command) {
        return CommandPreprocessor.translateCommand(command);
    }
}
