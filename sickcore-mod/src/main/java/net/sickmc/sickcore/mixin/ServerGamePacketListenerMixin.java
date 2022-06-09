package net.sickmc.sickcore.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.FilteredText;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.players.PlayerList;
import net.sickmc.sickcore.commonPlayer.SickPlayer;
import net.sickmc.sickcore.commonPlayer.SickPlayers;
import net.sickmc.sickcore.utils.Colors;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerMixin {

    @Shadow @Final private MinecraftServer server;

    @Redirect(method = "onDisconnect",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastSystemMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/resources/ResourceKey;)V"))
    public void onQuit(PlayerList instance, Component message, ResourceKey<ChatType> messageType){ }

    @Redirect(method = "broadcastChatMessage", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastChatMessage(Lnet/minecraft/server/network/FilteredText;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/resources/ResourceKey;)V"))
    public void onChat(PlayerList instance, FilteredText<PlayerChatMessage> filteredText, ServerPlayer serverPlayer, ResourceKey<ChatType> resourceKey){
        SickPlayer sickPlayer = SickPlayers.instance.getCachedEntity(serverPlayer.getUUID());
        MutableComponent newMessage = sickPlayer.getDisplayName().getName().copy();
        newMessage.append(Component.literal(" " + filteredText.raw().serverContent().getString()).withStyle(Style.EMPTY.withColor(Colors.INSTANCE.getLIGHT_GRAY()).withBold(false)));
        server.getPlayerList().broadcastSystemMessage(newMessage, resourceKey);
    }

}
