package me.anton.sickcore.games.defaults.all.maintenance;

import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.service.ICloudService;
import lombok.Getter;
import me.anton.sickcore.api.database.Finder;
import me.anton.sickcore.api.player.cloudPlayer.CloudAPIPlayer;
import me.anton.sickcore.core.BukkitCore;
import me.anton.sickcore.core.Core;
import org.bson.Document;

import java.util.UUID;

@Getter
public class MaintenanceModule {

    @Getter
    private static MaintenanceModule instance;
    public boolean active;
    public boolean secure;
    private Document maintenance;

    public void load() {
        instance = this;
        this.maintenance = Core.getInstance().getAppereanceModel().getDocument(Finder.stringFinder("type", "maintenance"));
        active = maintenance.getBoolean("active");
        secure = maintenance.getBoolean("secureMode");

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
        maintenance.replace("active", true);
        if (secure)maintenance.replace("secureMode", true);
        Core.getInstance().getAppereanceModel().updateDocument(Finder.stringFinder("type", "maintenance"), maintenance);
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
        maintenance.replace("active", false);
        maintenance.replace("secureMode", false);
        Core.getInstance().getAppereanceModel().updateDocument(Finder.stringFinder("type", "maintenance"), maintenance);
    }


}
