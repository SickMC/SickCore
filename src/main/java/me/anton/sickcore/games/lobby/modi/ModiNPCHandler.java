package me.anton.sickcore.games.lobby.modi;

import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.player.ICloudPlayer;
import eu.thesimplecloud.api.service.ICloudService;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class ModiNPCHandler extends BukkitHandler {

    public void addModiNPC(ICloudService service, IBukkitPlayer player, String npcName, PlayerInteractAtEntityEvent event){
        if (event.getRightClicked().getCustomName() == null) return;
        if (!event.getRightClicked().getCustomName().equalsIgnoreCase(npcName))return;
        ICloudPlayer cloudPlayer = player.api().cloud().cloudAPI();
        if (service == null) {player.sendMessage("§4This server is not available!", "§4Dieser Server ist nicht verfügbar!"); return;}
        cloudPlayer.connect(service);
        player.sendMessage("§7You were teleported to the Survival server!", "§7Du wurdest zum Survival Server teleportiert!");
        event.setCancelled(true);
    }

    @Override
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        ICloudService levelborder = CloudAPI.getInstance().getCloudServiceManager().getCloudServiceByName("LevelBorder-1");
        ICloudService survival = CloudAPI.getInstance().getCloudServiceManager().getCloudServiceByName("1.18-1");

        addModiNPC(levelborder, bukkitPlayer, "§6Level = Border", rawEvent);
        addModiNPC(survival, bukkitPlayer, "§6Survival 1.18", rawEvent);
    }
}
