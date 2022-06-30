package net.sickmc.sickcore.mixin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.sickmc.sickcore.games.Game;
import net.sickmc.sickcore.games.survival.Survival;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow
    protected abstract LootContext.Builder createLootContext(boolean causedByPlayer, DamageSource source);

    @Inject(method = "dropFromLootTable", at = @At(value = "HEAD"), cancellable = true)
    public void telekinesisLoot(DamageSource source, boolean causedByPlayer, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof Player)
            return;
        ResourceLocation resourceLocation = entity.getLootTable();
        LootTable lootTable = entity.level.getServer().getLootTables().get(resourceLocation);
        LootContext.Builder builder = createLootContext(causedByPlayer, source);
        lootTable.getRandomItems(builder.create(LootContextParamSets.ENTITY), item -> {
            if (item.isEmpty()) return;
            if (!causedByPlayer || !Game.current.getName().equals("Survival")) {
                entity.spawnAtLocation(item);
                ci.cancel();
                return;
            }
            Player player = (Player) source.getEntity();
            Survival survival = (Survival) Game.current;
            boolean telekinesis = EnchantmentHelper.getEnchantments(player.getMainHandItem()).containsKey(survival.telekinesis);
            if (!telekinesis) {
                entity.spawnAtLocation(item);
                ci.cancel();
                return;
            }
            if (!player.getInventory().add(item)) {
                entity.spawnAtLocation(item);
            }
            ci.cancel();
        });
    }
}