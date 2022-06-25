package net.sickmc.sickcore.mixin;

import net.minecraft.advancements.Advancement;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.sickmc.sickcore.commonPlayer.SickPlayer;
import net.sickmc.sickcore.commonPlayer.SickPlayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerAdvancements.class)
public class PlayerAdvancementMixin {

    @Shadow private ServerPlayer player;

    @Redirect(method = "award", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastSystemMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/resources/ResourceKey;)V"))
    public void changeAdvancementMessage(PlayerList instance, Component message, ResourceKey<ChatType> messageType, Advancement advancement){
        SickPlayer sickPlayer = SickPlayers.instance.getCachedEntity(player.getUUID());
        instance.broadcastSystemMessage(Component.translatable("chat.type.advancement." + advancement.getDisplay().getFrame().getName(), sickPlayer.getDisplayName().getName(), advancement.getChatComponent()), ChatType.SYSTEM);
    }

}
