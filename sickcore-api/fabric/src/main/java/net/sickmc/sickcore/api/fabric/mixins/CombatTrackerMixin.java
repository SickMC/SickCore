package net.sickmc.sickcore.api.fabric.mixins;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.CombatEntry;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.entity.LivingEntity;
import net.sickmc.sickcore.api.fabric.chat.ChatManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CombatTracker.class)
public abstract class CombatTrackerMixin {

    @Redirect(
        method = "getDeathMessage",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;getDisplayName()Lnet/minecraft/network/chat/Component;"
        )
    )
    public Component changeName(LivingEntity instance) {
        var currentChatManager = ChatManager.Companion.getCurrent();
        if (currentChatManager == null || !(instance instanceof ServerPlayer)) return instance.getDisplayName();
        return currentChatManager.changeDeathName((ServerPlayer) instance, instance.getCombatTracker());
    }

    @Redirect(
        method = "getDeathMessage",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/damagesource/CombatEntry;getAttackerName()Lnet/minecraft/network/chat/Component;"
        )
    )
    public Component changeAttackerName(CombatEntry instance) {
        var currentChatManager = ChatManager.Companion.getCurrent();
        if (currentChatManager == null || !(instance.getAttacker() instanceof ServerPlayer))
            return instance.getAttacker().getDisplayName();
        return currentChatManager.changeDeathName((ServerPlayer) instance.getAttacker(), ((ServerPlayer) instance.getAttacker()).getCombatTracker());
    }
}
