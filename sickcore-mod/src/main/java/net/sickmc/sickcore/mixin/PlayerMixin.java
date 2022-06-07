package net.sickmc.sickcore.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.sickmc.sickcore.commonPlayer.SickPlayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Shadow public abstract GameProfile getGameProfile();

    @Inject(method = "getDisplayName", at = @At("RETURN"), cancellable = true)
    public void getDisplayName(CallbackInfoReturnable<Component> cir){
        cir.setReturnValue(SickPlayers.instance.getCachedEntity(getGameProfile().getId()).getDisplayName().getName());
    }

}
