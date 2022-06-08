package net.sickmc.sickcore.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.arguments.MessageArgument;
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
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerMixin {

    @Shadow public ServerPlayer player;

    @Shadow @Final private MinecraftServer server;

    @Redirect(method = "onDisconnect",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastSystemMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/resources/ResourceKey;)V"))
    public void onQuit(PlayerList instance, Component message, ResourceKey<ChatType> messageType){ }

   /* @Inject(method = "broadcastChatMessage", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastChatMessage(Lnet/minecraft/server/network/FilteredText;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/resources/ResourceKey;)V"), cancellable = true)
    public void onChat(FilteredText<PlayerChatMessage> filteredText, CallbackInfo ci){
        SickPlayer sickPlayer = SickPlayers.instance.getCachedEntity(player.getUUID());
        Component newMessage = sickPlayer.getDisplayName().getName().copy().append(Component.literal("   " + filteredText.raw().serverContent().getString())).withStyle(ChatFormatting.getById(Colors.INSTANCE.getLIGHT_GRAY()));
        System.out.println("überhaupt???");
        server.getPlayerList().broadcastSystemMessage(newMessage, ChatType.CHAT);
        System.out.println("jkaaasdsadaa????");
        server.getPlayerList().broadcastChatMessage(new FilteredText<PlayerChatMessage>(new PlayerChatMessage(newMessage, MessageSignature.unsigned(), Optional.empty()), null), player, ChatType.CHAT);
        System.out.println("jkaaaaa????");
    }
    */
}
