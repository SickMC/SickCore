package net.sickmc.sickcore.api.fabric.mixins;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.CombatTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CombatTracker.class)
public class CombatTrackerMixin {

    @Inject(method = "getDeathMessage", at = @At(value = "RETURN"))
    public void changeColor(CallbackInfoReturnable<Component> cir, Component component3) {

    }

}
