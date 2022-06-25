package net.sickmc.sickcore.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.CombatEntry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.sickmc.sickcore.commonPlayer.SickPlayer;
import net.sickmc.sickcore.commonPlayer.SickPlayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CombatEntry.class)
public abstract class CombatEntryMixin {

    @Shadow
    public abstract DamageSource getSource();

    @Inject(method = "getAttackerName", at = @At(value = "HEAD"))
    public void customAttackerName(CallbackInfoReturnable<Component> cir) {
        if (!(getSource().getEntity() instanceof Player)) return;
        SickPlayer sickPlayer = SickPlayers.instance.getCachedEntity(getSource().getEntity().getUUID());
        if (this.getSource().getEntity() == null) cir.setReturnValue(null);
        else cir.setReturnValue(sickPlayer.getDisplayName().getName());
    }

}
