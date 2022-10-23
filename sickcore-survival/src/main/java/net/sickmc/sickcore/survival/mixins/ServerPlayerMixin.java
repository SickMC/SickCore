package net.sickmc.sickcore.survival.mixins;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.sickmc.sickcore.survival.CombatLock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {

    @Inject(
        method = "hurt",
        at = @At(
            value = "TAIL"
        )
    )
    public void onPlayerHurt(DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
        if (!(damageSource.getEntity() instanceof ServerPlayer)) return;
        CombatLock.INSTANCE.playerHit((ServerPlayer) (Object) this, (ServerPlayer) damageSource.getEntity());
    }
}
