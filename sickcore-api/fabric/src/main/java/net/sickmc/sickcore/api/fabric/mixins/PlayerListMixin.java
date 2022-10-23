package net.sickmc.sickcore.api.fabric.mixins;

import net.minecraft.ChatFormatting;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.sickmc.sickcore.api.fabric.chat.ChatManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerList.class)
public abstract class PlayerListMixin {

    @Shadow
    public abstract void broadcastSystemMessage(Component component, boolean bl);

    @Redirect(
        method = "placeNewPlayer",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/players/PlayerList;broadcastSystemMessage(Lnet/minecraft/network/chat/Component;Z)V"
        )
    )
    public void disabledJoinMessage(PlayerList instance, Component component, boolean bl, Connection connection, ServerPlayer player) {
        var currentChatManager = ChatManager.Companion.getCurrent();
        if (currentChatManager == null)
            broadcastSystemMessage(Component.translatable("multiplayer.player.joined", player.getDisplayName()).withStyle(ChatFormatting.YELLOW), false);
    }
}
