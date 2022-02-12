package me.anton.sickcore.modules.notify;

import eu.thesimplecloud.api.CloudAPI;
import lombok.Getter;
import me.anton.sickcore.core.BukkitCore;
import me.anton.sickcore.core.BungeeCore;
import me.anton.sickcore.core.Core;
import me.anton.sickcore.core.module.globalmodule.GlobalModule;
import me.anton.sickcore.modules.notify.notifys.ServiceNotify;
import me.anton.sickcore.modules.notify.notifys.TeamChatNotify;

@Getter
public class NotifyModule extends GlobalModule {

    @Getter
    private static NotifyModule instance;

    @Override
    public void load() {
        instance = this;

        switch (Core.getInstance().getEnvironment()){
            case BUNGEECORD -> {
                CloudAPI.getInstance().getEventManager().registerListener(CloudAPI.getInstance().getThisSidesCloudModule(), new ServiceNotify());
                BungeeCore.getInstance().getManager().registerCommand(new TeamChatNotify());
            }
            case BUKKIT -> {
                BukkitCore.getInstance().getManager().registerCommand(new NotifyCommand(), true);
            }
        }
    }

    @Override
    public void unload() {
        instance = null;

    }



}
