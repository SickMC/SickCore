package net.sickmc.sickcore.api.fabric.mixins;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.sickmc.sickcore.api.fabric.FabricEntrypoint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerMixin {

    @Shadow
    public ServerPlayer player;

    @Inject(method = "onDisconnect", at = @At(value = "HEAD"))
    public void onQuit(Component reason, CallbackInfo ci) {
        FabricEntrypoint.INSTANCE.quit(player.getUUID());
    }
}
