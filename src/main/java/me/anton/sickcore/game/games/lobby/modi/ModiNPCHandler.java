package me.anton.sickcore.game.games.lobby.modi;

import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.player.ICloudPlayer;
import eu.thesimplecloud.api.service.ICloudService;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.apiPlayer.language.LanguageObject;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.utils.common.Replacable;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class ModiNPCHandler extends BukkitHandler {

    public void addModiNPC(ICloudService service, IBukkitPlayer player, String npcName, String modiName, PlayerInteractAtEntityEvent event){
        if (event.getRightClicked().getCustomName() == null) return;
        if (!event.getRightClicked().getCustomName().equalsIgnoreCase(npcName))return;
        ICloudPlayer cloudPlayer = player.api().cloud().cloudAPI();
        if (service == null) {player.sendMessage(LanguagePath.PAPER_COMMAND_LOBBY_MODI_MODINPCHANDLER_SERVERNOTAVAILABLE); return;}
        cloudPlayer.connect(service);
        player.sendMessage(new LanguageObject(player.api(), LanguagePath.PAPER_COMMAND_LOBBY_MODI_MODINPCHANDLER_TELEPORTSUCCESS).replace(new Replacable("%modiName%", modiName)));
        event.setCancelled(true);
    }

    @Override
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        ICloudService levelborder = CloudAPI.getInstance().getCloudServiceManager().getCloudServiceByName("LevelBorder-1");
        ICloudService survival = CloudAPI.getInstance().getCloudServiceManager().getCloudServiceByName("Survival-1");

        addModiNPC(levelborder, bukkitPlayer, "§6Level = Border", "Level = Border", rawEvent);
        addModiNPC(survival, bukkitPlayer, "§6Survival 1.18", "Survival 1.18", rawEvent);
    }
}
