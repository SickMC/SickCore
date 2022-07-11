package net.sickmc.sickcore.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.sickmc.sickcore.common.chat.DeathEvent;
import net.sickmc.sickcore.common.chat.DeathNameEvent;
import net.sickmc.sickcore.games.Game;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CombatTracker.class)
public class CombatTrackerMixin {

    @Shadow
    @Final
    private LivingEntity mob;

    @Inject(method = "getDeathMessage", at = @At(value = "HEAD"), cancellable = true)
    public void overrideDeathMessage(CallbackInfoReturnable<Component> cir) {
        var event = Game.current.getChat().getDeathEvent();
        if (event == null || !(mob instanceof Player)) {
            cir.setReturnValue(cir.getReturnValue());
            return;
        }
        if (event.isEmpty()){
            cir.cancel();
            return;
        }
        cir.setReturnValue(event.get().invoke(new DeathEvent((ServerPlayer) mob, mob.getLevel(), (CombatTracker) (Object) this)));
    }

    @Redirect(method = "getDeathMessage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getDisplayName()Lnet/minecraft/network/chat/Component;"))
    public Component changeDeathName(LivingEntity instance) {
        var event = Game.current.getChat().getDeathNameEvent();
        if (event == null || !(mob instanceof Player)) return (instance.getDisplayName());
        return event.invoke(new DeathNameEvent((ServerPlayer) mob, mob.getLevel()));
    }

}
