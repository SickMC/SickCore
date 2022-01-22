package me.anton.sickcore.games.defaults.heads;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.utils.common.math.Randoms;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobDrops extends BukkitHandler {

    @Override
    public void onEntityDeathByPlayer(Entity entity, EntityDeathEvent rawEvent, IBukkitPlayer killer) {
        Player bkiller = killer.getPlayer();
        if (Randoms.getRandomNumberInRange(1,64) == 1){bkiller.getWorld().dropItem(entity.getLocation(), new MobHeads().getMobHead(entity));}
        if (entity.getType().equals(EntityType.SHEEP)){
            if (Randoms.getRandomNumberInRange(1,1000) == 1){
                bkiller.getWorld().dropItem(entity.getLocation(), new MobHeads().getSpecialHead(entity));
            }
        }
    }
}
