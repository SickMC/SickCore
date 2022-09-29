package net.sickmc.sickcore.api.fabric.mixins;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.sickmc.sickcore.api.fabric.chat.ChatManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DamageSource.class)
public class DamageSourceMixin {

    @Redirect(
        method = "getLocalizedDeathMessage",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;getDisplayName()Lnet/minecraft/network/chat/Component;"
        )
    )
    public Component changeDeathName(LivingEntity instance) {
        var currentChatManager = ChatManager.Companion.getCurrent();
        if (currentChatManager == null || !(instance instanceof ServerPlayer)) return instance.getDisplayName();
        var result = currentChatManager.changeDeathName((ServerPlayer) instance, instance.getCombatTracker());
        return result == null ? instance.getDisplayName() : result;
    }
}
