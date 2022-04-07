package me.anton.sickcore.games.survival.heads;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.utils.common.MathUtils;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.anton.sickcore.games.survival.SurvivalGamePlayer;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class MobDrops extends BukkitHandler {

    @Override
    public void onEntityDeathByPlayer(Entity entity, EntityDeathEvent rawEvent, BukkitPlayer killer) {
        HeadDatabaseAPI dbAPI = HeadDBAPI.getApi();
        Player bkiller = killer.getPlayer();
        SurvivalGamePlayer gamePlayer = new SurvivalGamePlayer(killer.getPlayer().getUniqueId());
        if (MathUtils.getRandomNumberInRange(1,64) == 1){
            bkiller.getWorld().dropItem(entity.getLocation(), getMobItem(entity));
            gamePlayer.completeHead(getMobHead(entity));
        }
        if (entity.getType().equals(EntityType.SHEEP)){
            if (MathUtils.getRandomNumberInRange(1,1000) == 1){
                MobHead head = MobHead.SPECIAL_SHEEP;
                ItemStack s = dbAPI.getItemHead(String.valueOf(head.getId()));
                bkiller.getWorld().dropItem(entity.getLocation(), new ItemBuilder(s, killer).setName("§5" + head.getHeadName()).setLore("§7Chance: 1 to 1000").build());
                gamePlayer.completeHead(MobHead.SPECIAL_SHEEP);
            }
        }
    }

    public static ItemStack getMobItem(Entity entity){
        HeadDatabaseAPI dbAPI = HeadDBAPI.getApi();
        String prefix = "§e";
        MobHead head = getMobHead(entity);
        if (entity.getType() == EntityType.VILLAGER){
            Villager villager = (Villager) entity;
            return new ItemBuilder(dbAPI.getItemHead(String.valueOf(head.getId())), null).setName(prefix + head.getHeadName()).build();
        }
        if (entity.getType() == EntityType.FOX){
            Fox fox = (Fox) entity;
            return new ItemBuilder(dbAPI.getItemHead(String.valueOf(head.getId())), null).setName(prefix + head.getHeadName()).build();
        }
        if (entity.getType() == EntityType.AXOLOTL){
            Axolotl axolotl = (Axolotl) entity;
            return new ItemBuilder(dbAPI.getItemHead(String.valueOf(head.getId())), null).setName(prefix + head.getHeadName()).build();
        }
        if (entity.getType() == EntityType.SHEEP){
            Sheep sheep = (Sheep) entity;
            return new ItemBuilder(dbAPI.getItemHead(String.valueOf(head.getId())), null).setName(prefix + head.getHeadName()).build();
        }
        if (entity.getType() == EntityType.CAT){
            Cat cat = (Cat) entity;
            return new ItemBuilder(dbAPI.getItemHead(String.valueOf(head.getId())), null).setName(prefix + head.getHeadName()).build();
        }
        if (entity.getType() == EntityType.HORSE){
            Horse horse = (Horse) entity;
            return new ItemBuilder(dbAPI.getItemHead(String.valueOf(head.getId())), null).setName(prefix + head.getHeadName()).build();
        }
        if (entity.getType() == EntityType.LLAMA){
            Llama llama = (Llama) entity;
            return new ItemBuilder(dbAPI.getItemHead(String.valueOf(head.getId())), null).setName(prefix + head.getHeadName()).build();
        }
        if (entity.getType() == EntityType.PARROT){
            Parrot parrot = (Parrot) entity;
            return new ItemBuilder(dbAPI.getItemHead(String.valueOf(head.getId())), null).setName(prefix + head.getHeadName()).build();
        }
        if (entity.getType() == EntityType.MUSHROOM_COW){
            MushroomCow mushroomCow = (MushroomCow) entity;
            String name = mushroomCow.getType().name().replace("_", "");
            return new ItemBuilder(dbAPI.getItemHead(String.valueOf(head.getId())), null).setName(prefix + head.getHeadName()).build();
        }
        if (entity.getType() == EntityType.TRADER_LLAMA){
            TraderLlama traderLlama = (TraderLlama) entity;
            String name = traderLlama.getType().name().replace("_", "");
            return new ItemBuilder(dbAPI.getItemHead(String.valueOf(head.getId())), null).setName(prefix + head.getHeadName()).build();
        }

        return new ItemBuilder(dbAPI.getItemHead(String.valueOf(head.getId())), null).setName(prefix + head.getHeadName()).build();
    }

    public static MobHead getMobHead(Entity entity){
        String prefix = "§e";

        if (entity.getType() == EntityType.VILLAGER){
            Villager villager = (Villager) entity;
            return MobHead.valueOf(villager.getType().name() + "_" + villager.getProfession().name());
        }
        if (entity.getType() == EntityType.FOX){
            Fox fox = (Fox) entity;
            return MobHead.valueOf(fox.getType().name() + "_" + fox.getFoxType().name());
        }
        if (entity.getType() == EntityType.AXOLOTL){
            Axolotl axolotl = (Axolotl) entity;
            return MobHead.valueOf(axolotl.getType().name() + "_" + axolotl.getVariant().name());
        }
        if (entity.getType() == EntityType.SHEEP){
            Sheep sheep = (Sheep) entity;
            return MobHead.valueOf(sheep.getType().name() + "_" + sheep.getColor().name());
        }
        if (entity.getType() == EntityType.CAT){
            Cat cat = (Cat) entity;
            return MobHead.valueOf(cat.getType().name() + "_" + cat.getCatType().name());
        }
        if (entity.getType() == EntityType.HORSE){
            Horse horse = (Horse) entity;
            return MobHead.valueOf(horse.getType().name() + "_" + horse.getColor().name());
        }
        if (entity.getType() == EntityType.LLAMA){
            Llama llama = (Llama) entity;
            return MobHead.valueOf(llama.getType().name() + "_" + llama.getColor().name());
        }
        if (entity.getType() == EntityType.PARROT){
            Parrot parrot = (Parrot) entity;
            return MobHead.valueOf(parrot.getType().name() + "_" + parrot.getVariant().name());
        }
        if (entity.getType() == EntityType.MUSHROOM_COW){
            MushroomCow mushroomCow = (MushroomCow) entity;
            String name = mushroomCow.getType().name().replace("_", "");
            return MobHead.valueOf(name + "_" + mushroomCow.getVariant().name());
        }
        if (entity.getType() == EntityType.TRADER_LLAMA){
            TraderLlama traderLlama = (TraderLlama) entity;
            String name = traderLlama.getType().name().replace("_", "");
            return MobHead.valueOf(name + "_" + traderLlama.getColor().name());
        }

        return MobHead.valueOf(entity.getType().name());
    }
}
