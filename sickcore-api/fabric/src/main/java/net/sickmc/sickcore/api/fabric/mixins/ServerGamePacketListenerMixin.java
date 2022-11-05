package net.sickmc.sickcore.api.fabric.mixins;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.players.PlayerList;
import net.sickmc.sickcore.api.fabric.FabricEntrypoint;
import net.sickmc.sickcore.api.fabric.chat.ChatManager;
import net.sickmc.sickcore.api.fabric.moderation.ChatModeration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerMixin {

    @Shadow
    public ServerPlayer player;

    @Shadow
    protected abstract void detectRateSpam();

    @Redirect(
        method = "onDisconnect",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/players/PlayerList;broadcastSystemMessage(Lnet/minecraft/network/chat/Component;Z)V"
        )
    )
    public void manipulateQuitMessage(PlayerList instance, Component component, boolean bl) {
        var currentChatManager = ChatManager.Companion.getCurrent();
        if (currentChatManager == null){
            instance.broadcastSystemMessage(Component.translatable("multiplayer.player.left", this.player.getDisplayName()).withStyle(ChatFormatting.YELLOW), false);
            return;
        }
        ChatManager.Companion.getCurrent().handleQuit(player, instance);
    }

    @Inject(
        method = "onDisconnect",
        at = @At(
            value = "TAIL"
        )
    )
    public void onQuit(Component reason, CallbackInfo ci) {
        FabricEntrypoint.INSTANCE.quit(player);
    }

    @Redirect(
        method = "broadcastChatMessage",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/players/PlayerList;broadcastChatMessage(Lnet/minecraft/network/chat/PlayerChatMessage;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/network/chat/ChatType$Bound;)V"
        )
    )
    public void manipulateChat(PlayerList instance, PlayerChatMessage playerChatMessage, ServerPlayer serverPlayer, ChatType.Bound bound) {
        if (ChatModeration.INSTANCE.getMutedPlayers().contains(player.getUUID())) return;
        var currentChatManager = ChatManager.Companion.getCurrent();
        if (currentChatManager == null) {
            instance.broadcastChatMessage(playerChatMessage, this.player, ChatType.bind(ChatType.CHAT, this.player));
            detectRateSpam();
            return;
        }
        currentChatManager.handleChat(player, playerChatMessage.signedContent(), instance);
    }
}
