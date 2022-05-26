package net.sickmc.sickcore.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.network.TextFilter;
import net.sickmc.sickcore.core.games.Game;
import net.sickmc.sickcore.core.games.survival.CommonEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(ServerGamePacketListenerImpl.class)
public class MixinChat {

    @Shadow public ServerPlayer player;

    @Shadow @Final private MinecraftServer server;

    @Inject(method = "handleChat*",
            at = @At(value = "RETURN"))
    private void handleChat(TextFilter.FilteredText message) {
        if (Game.current.getName().equals("Survival")) CommonEvents.INSTANCE.chat(message, player, server);
    }

}
