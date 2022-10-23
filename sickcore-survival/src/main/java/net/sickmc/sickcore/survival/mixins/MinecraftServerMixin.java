package net.sickmc.sickcore.survival.mixins;

import net.minecraft.server.MinecraftServer;
import net.sickmc.sickcore.survival.SurvivalEntrypoint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Inject(method = "tickServer", at = @At("TAIL"))
    public void onTick(BooleanSupplier booleanSupplier, CallbackInfo ci) {
        SurvivalEntrypoint.INSTANCE.tick();
    }
}
