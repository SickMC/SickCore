package net.sickmc.sickcore.mixin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow
    public abstract ResourceLocation getLootTable();

    @Shadow
    protected abstract LootContext.Builder createLootContext(boolean causedByPlayer, DamageSource source);

    @Inject(method = "dropFromLootTable", at = @At("RETURN"))
    public void telekinesisDrops(DamageSource source, boolean causedByPlayer, CallbackInfo ci) {
        if (!causedByPlayer) return;
        Player player = (Player) source.getEntity();
        LivingEntity entity = (LivingEntity) (Object) this;
        ResourceLocation resourceLocation = getLootTable();
        LootTable lootTable = entity.level.getServer().getLootTables().get(resourceLocation);
        LootContext.Builder builder = createLootContext(true, source);
        lootTable.getRandomItems(builder.create(LootContextParamSets.ENTITY), item -> {
            if (!player.getInventory().add(item)) entity.spawnAtLocation(item);
        });
    }


}
