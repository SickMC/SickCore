package net.sickmc.sickcore.mixin;

import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.FilteredText;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.players.PlayerList;
import net.sickmc.sickcore.common.chat.ChatEvent;
import net.sickmc.sickcore.common.chat.QuitEvent;
import net.sickmc.sickcore.games.Game;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerMixin {

    @Shadow
    @Final
    private MinecraftServer server;

    @Shadow
    public ServerPlayer player;

    @Redirect(method = "onDisconnect", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastSystemMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/resources/ResourceKey;)V"))
    public void onQuit(PlayerList instance, Component message, ResourceKey<ChatType> messageType) {
        var event = Game.current.getChat().getQuitMessage();
        if (event == null) {
            instance.broadcastSystemMessage(message, messageType);
            return;
        }
        if (event.isEmpty()) {
            return;
        }
        instance.broadcastSystemMessage(event.get().invoke(new QuitEvent(player, player.level)), ChatType.SYSTEM);
    }

    @Redirect(method = "broadcastChatMessage", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastChatMessage(Lnet/minecraft/server/network/FilteredText;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/resources/ResourceKey;)V"))
    public void onChat(PlayerList instance, FilteredText<PlayerChatMessage> filteredText, ServerPlayer serverPlayer, ResourceKey<ChatType> resourceKey) {
        var event = Game.current.getChat().getChatEvent();
        if (event == null) {
            instance.broadcastChatMessage(filteredText, player, ChatType.CHAT);
            return;
        }
        if (event.isEmpty()) {
            return;
        }
        instance.broadcastSystemMessage(event.get().invoke(new ChatEvent(player, filteredText.raw().serverContent().getString(), player.level)), ChatType.SYSTEM);
    }

}
