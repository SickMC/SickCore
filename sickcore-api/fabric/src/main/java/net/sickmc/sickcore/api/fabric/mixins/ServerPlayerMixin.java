package net.sickmc.sickcore.api.fabric.mixins;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.sickmc.sickcore.api.fabric.chat.ChatManager;
import net.sickmc.sickcore.api.fabric.tablist.Tablist;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

    ServerPlayer player = (ServerPlayer) (Object) this;

    @Inject(method = "getTabListDisplayName", at = @At("RETURN"), cancellable = true)
    public void onGetTablistName(CallbackInfoReturnable<Component> cir) {
        Tablist tablist = Tablist.getCurrentTablist();
        if (tablist == null) return;
        var generatedName = tablist.getPlayerNames().get(player.getUUID());
        if (generatedName == null) return;
        cir.setReturnValue(generatedName.component1());
    }

    @Redirect(method = "die", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastSystemMessage(Lnet/minecraft/network/chat/Component;Z)V"))
    public void manipulateDeathMessage(PlayerList instance, Component component, boolean bl) {
        var currentChatManager = ChatManager.Companion.getCurrent();
        var tracker = player.getCombatTracker();
        if (currentChatManager == null) {
            instance.broadcastSystemMessage(tracker.getDeathMessage(), false);
            return;
        }
        currentChatManager.handleDeath(player, tracker, instance);
    }
}
