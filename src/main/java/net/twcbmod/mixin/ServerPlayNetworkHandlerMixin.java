package net.twcbmod.mixin;

import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.twcbmod.translator.CommandPreprocessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {

    @ModifyVariable(method = "executeCommand", at = @At("HEAD"), argsOnly = true)
    private String onExecuteCommand(String command) {
        return CommandPreprocessor.translateCommand(command);
    }
}
