package net.sickmc.sickcore.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.sickmc.sickcore.games.Game;
import net.sickmc.sickcore.games.survival.Survival;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

@Mixin(Block.class)
public abstract class BlockMixin {

    @Inject(method = "getDrops(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;", at = @At("RETURN"), cancellable = true)
    private static void handleTelekinesis(BlockState state, ServerLevel world, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity entity, ItemStack stack, CallbackInfoReturnable<List<ItemStack>> cir) {
        if (!(entity instanceof Player player)) return;
        if (!Game.current.getName().equals("Survival")) return;
        Survival survival = (Survival) Game.current;
        boolean telekinesis = EnchantmentHelper.getEnchantments(stack).containsKey(survival.telekinesis);
        if (!telekinesis) return;

        ArrayList<ItemStack> itemsToDrop = new ArrayList<>();
        for (ItemStack itemStack : cir.getReturnValue())
            if (!player.getInventory().add(itemStack)) itemsToDrop.add(itemStack);

        cir.setReturnValue(itemsToDrop);
    }

}


