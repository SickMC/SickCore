package net.sickmc.sickcore.mixin;

import net.minecraft.network.Connection;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.sickmc.sickcore.SickHandler;
import net.sickmc.sickcore.common.chat.JoinEvent;
import net.sickmc.sickcore.common.chat.JoinMessageController;
import net.sickmc.sickcore.common.chat.JoinMessagePresets;
import net.sickmc.sickcore.games.Game;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public class PlayerListMixin {

    @Redirect(method = "placeNewPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastSystemMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/resources/ResourceKey;)V"))
    public void placeNewPlayer(PlayerList instance, Component message, ResourceKey<ChatType> messageType, Connection connection, ServerPlayer player) {
        var event = Game.current.getChat().getDefaultJoinMessage();
        var presetJoin = Game.current.getChat().getJoinMessage();
        if (event == null && presetJoin == JoinMessagePresets.NONE) {
            instance.broadcastSystemMessage(message, messageType);
            return;
        }
        if (event != null && presetJoin == JoinMessagePresets.NONE) {
            if (event.isEmpty()) {
                return;
            }
            instance.broadcastSystemMessage(event.get().invoke(new JoinEvent(player, player.level)), ChatType.SYSTEM);
            return;
        }
        JoinMessageController.INSTANCE.handle(presetJoin, player);
    }

    @Inject(method = "placeNewPlayer", at = @At(value = "HEAD"))
    public void loadPlayer(Connection connection, ServerPlayer player, CallbackInfo ci) {
        SickHandler.INSTANCE.reloadPlayer(player);
    }

}
