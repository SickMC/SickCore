package me.anton.sickcore.games.all.maintenance;

import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.service.ICloudService;
import lombok.Getter;
import me.anton.sickcore.api.database.Finder;
import me.anton.sickcore.api.player.cloudPlayer.CloudAPIPlayer;
import me.anton.sickcore.core.BukkitCore;
import me.anton.sickcore.core.Core;
import me.anton.sickcore.core.UtilConfiguration;
import me.anton.sickcore.core.module.globalmodule.GlobalModule;
import org.bson.Document;

import java.util.UUID;

@Getter
public class MaintenanceModule {

    @Getter
    private static MaintenanceModule instance;
    public boolean active;
    public boolean secure;
    public UtilConfiguration config;

    public void load() {
        instance = this;
        this.config = new UtilConfiguration("maintenance");
        active = config.getDocument().getBoolean("active");
        secure = config.getDocument().getBoolean("secureMode");

        register();
    }

    public void unload() {

    }

    public void register() {
        BukkitCore.getInstance().getManager().registerCommand(new MaintenanceCommand());
    }

    public void enable(boolean secure){
        this.secure = secure;
        active = true;
        Document document = config.getDocument();
        document.replace("active", true);
        if (secure)document.replace("secureMode", true);
        config.update(document);
        for (ICloudService service : CloudAPI.getInstance().getCloudServiceManager().getAllCachedObjects()){
            if (!service.isLobby())return;
            service.getOnlinePlayers().getBlocking().forEach(player -> {
                if (!secure)
                    if (!new CloudAPIPlayer(player.getUniqueId()).api().isTeam())
                        player.getCloudPlayer().getBlocking().kick("Maintenance is now enabled!");
                else
                    if(!player.getUniqueId().equals(UUID.fromString("84c7eef5-ae2c-4ebb-a006-c3ee07643d79")))
                        player.getCloudPlayer().getBlocking().kick("Maintenance is now enabled!");
            });
        }
    }

    public void disable(){
        this.secure = false;
        this.active = false;
        Document document = config.getDocument();
        document.replace("active", false);
        document.replace("secureMode", false);
        getConfig().update(document);
    }


}
