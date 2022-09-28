package net.sickmc.sickcore.api.fabric.mixins;

import net.minecraft.advancements.Advancement;
import net.minecraft.network.chat.Component;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.sickmc.sickcore.api.fabric.chat.ChatManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerAdvancements.class)
public class AdvancementsMixin {

    @Shadow
    private ServerPlayer player;

    @Redirect(method = "award", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastSystemMessage(Lnet/minecraft/network/chat/Component;Z)V"))
    public void manipulateAdvancementMessage(PlayerList instance, Component component, boolean bl, Advancement advancement) {
        var currentChatManager = ChatManager.Companion.getCurrent();
        if (currentChatManager == null) {
            instance.broadcastSystemMessage(Component.translatable("chat.type.advancement." + advancement.getDisplay().getFrame().getName(), player.getDisplayName(), advancement.getChatComponent()), false);
            return;
        }
        currentChatManager.handleAdvancement(player, advancement, instance);
    }
}
