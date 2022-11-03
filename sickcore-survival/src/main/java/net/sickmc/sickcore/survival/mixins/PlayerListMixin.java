package net.sickmc.sickcore.survival.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.chat.Component;
import net.minecraft.server.players.PlayerList;
import net.sickmc.sickcore.survival.CombatLock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.SocketAddress;

@Mixin(PlayerList.class)
public class PlayerListMixin {

    @Inject(
        method = "canPlayerLogin",
        at = @At(
            value = "TAIL"
        ),
        cancellable = true
    )
    public void onPreJoin(SocketAddress socketAddress, GameProfile gameProfile, CallbackInfoReturnable<Component> cir) {
        if (!CombatLock.INSTANCE.getBlockedPlayers().containsKey(gameProfile.getId())) return;
        if (CombatLock.INSTANCE.getBlockedPlayers().get(gameProfile.getId()) <= System.currentTimeMillis()) {
            CombatLock.INSTANCE.getBlockedPlayers().remove(gameProfile.getId());
            return;
        }
        cir.setReturnValue(CombatLock.INSTANCE.playerJoin(gameProfile.getId()));
    }
}
