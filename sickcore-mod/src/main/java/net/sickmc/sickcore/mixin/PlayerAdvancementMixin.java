package net.sickmc.sickcore.mixin;

import net.minecraft.advancements.Advancement;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.sickmc.sickcore.common.chat.AdvancementEvent;
import net.sickmc.sickcore.games.Game;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerAdvancements.class)
public class PlayerAdvancementMixin {

    @Shadow
    private ServerPlayer player;

    @Redirect(method = "award", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastSystemMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/resources/ResourceKey;)V"))
    public void changeAdvancementMessage(PlayerList instance, Component message, ResourceKey<ChatType> messageType, Advancement advancement) {
        var event = Game.current.getChat().getAdvancementEvent();
        if (event == null){
            instance.broadcastSystemMessage(message, messageType);
            return;
        }
        if (event.isEmpty()){
            return;
        }
        event.get().invoke(new AdvancementEvent(player, player.level, advancement, (PlayerAdvancements) (Object) this));
    }

}
