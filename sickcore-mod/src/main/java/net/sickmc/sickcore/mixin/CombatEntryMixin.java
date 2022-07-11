package net.sickmc.sickcore.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.CombatEntry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.sickmc.sickcore.common.chat.GetAttackerNameEvent;
import net.sickmc.sickcore.games.Game;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CombatEntry.class)
public abstract class CombatEntryMixin {

    @Shadow
    public abstract DamageSource getSource();

    @Shadow
    public abstract @Nullable String getLocation();

    @Inject(method = "getAttackerName", at = @At(value = "HEAD"), cancellable = true)
    public void customAttackerName(CallbackInfoReturnable<Component> cir) {
        if (!(getSource().getEntity() instanceof Player)) return;
        var event = Game.current.getChat().getGetAttackerNameEvent();
        if (event == null || getSource().getEntity() == null) {
            cir.setReturnValue(cir.getReturnValue());
            return;
        }
        cir.setReturnValue(event.invoke(new GetAttackerNameEvent((ServerPlayer) getSource().getEntity(), getSource().getEntity().getLevel())));
    }

}
