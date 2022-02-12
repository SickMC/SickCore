package me.anton.sickcore.game.games.build.tools.invisitemframes;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class InvisFrameUpdate extends BukkitHandler {

    private static NamespacedKey invisibleKey = new NamespacedKey(BukkitCore.getInstance().getPlugin(), "invisible");
    private Set<DroppedFrameLocation> droppedFrames = new HashSet<>();

    @Override
    public void onHangingBreak(HangingBreakEvent rawEvent, Hanging hanging) {
        DroppedFrameLocation droppedFrameLocation = new DroppedFrameLocation(hanging.getLocation());
        droppedFrames.add(droppedFrameLocation);
        droppedFrameLocation.setRemoval((new BukkitRunnable()
        {
            @Override
            public void run()
            {
                droppedFrames.remove(droppedFrameLocation);
            }
        }).runTaskLater(BukkitCore.getInstance().getPlugin(), 20L));
    }

    @Override
    public void onItemSpawn(ItemSpawnEvent rawEvent, Item item, World world) {
        Iterator<DroppedFrameLocation> iter = droppedFrames.iterator();
        while(iter.hasNext())
        {
            DroppedFrameLocation droppedFrameLocation = iter.next();
            if(droppedFrameLocation.isFrame(item)){
                ItemStack frame = InvisItemFrame.generateInvisibleItemFrame();

                item.setItemStack(frame);

                droppedFrameLocation.getRemoval().cancel();
                iter.remove();
                break;
            }
        }
    }

    @Override
    public void onPlayerInteractEntity(Entity entity, PlayerInteractEntityEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        if(isFrameEntity(entity) &&
                entity.getPersistentDataContainer().has(invisibleKey, PersistentDataType.BYTE))
        {
            ItemFrame frame = (ItemFrame) entity;
            Bukkit.getScheduler().runTaskLater(BukkitCore.getInstance().getPlugin(), () ->
            {
                if(frame.getItem().getType() != Material.AIR)
                {
                    frame.setGlowing(false);
                    frame.setVisible(false);
                }
            }, 1L);
        }
    }

    @Override
    public void onHangingPlace(HangingPlaceEvent rawEvent, Hanging hanging, IBukkitPlayer handler) {
        if(!isFrameEntity(hanging) || handler.getPlayer() == null)
        {
            return;
        }

        // Get the frame item that the player placed
        ItemStack frame;
        Player p = handler.getPlayer();
        if(p.getInventory().getItemInMainHand().getType() == Material.ITEM_FRAME)
        {
            frame = p.getInventory().getItemInMainHand();
        }
        else if(p.getInventory().getItemInOffHand().getType() == Material.ITEM_FRAME)
        {
            frame = p.getInventory().getItemInOffHand();
        }
        else
        {
            return;
        }

        // If the frame item has the invisible tag, make the placed item frame invisible
        if(frame.getItemMeta().getPersistentDataContainer().has(invisibleKey, PersistentDataType.BYTE))
        {
            ItemFrame itemFrame = (ItemFrame) hanging;

            itemFrame.setVisible(false);

            hanging.getPersistentDataContainer().set(invisibleKey, PersistentDataType.BYTE, (byte) 1);
        }
    }

    private boolean isFrameEntity(Entity entity){
        return (entity != null && (entity.getType() == EntityType.ITEM_FRAME));
    }
}
