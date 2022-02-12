package me.anton.sickcore.game.defaults.additional.heads;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.utils.common.MathUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobDrops extends BukkitHandler {

    @Override
    public void onEntityDeathByPlayer(Entity entity, EntityDeathEvent rawEvent, IBukkitPlayer killer) {
        Player bkiller = killer.getPlayer();
        if (MathUtils.getRandomNumberInRange(1,64) == 1){bkiller.getWorld().dropItem(entity.getLocation(), new MobHeads().getMobHead(entity));}
        if (entity.getType().equals(EntityType.SHEEP)){
            if (MathUtils.getRandomNumberInRange(1,1000) == 1){
                bkiller.getWorld().dropItem(entity.getLocation(), new MobHeads().getSpecialHead(entity));
            }
        }
    }
}
