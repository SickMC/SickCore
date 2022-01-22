package me.anton.sickcore.games.survival.appereance;

import eu.thesimplecloud.api.event.player.CloudPlayerDisconnectEvent;
import eu.thesimplecloud.api.event.player.CloudPlayerLoginEvent;
import eu.thesimplecloud.api.eventapi.CloudEventHandler;
import eu.thesimplecloud.api.eventapi.IListener;
import eu.thesimplecloud.module.permission.event.player.PermissionPlayerUpdatedEvent;
import me.anton.sickcore.games.lobby.appereance.LobbyTablist;

public class SurvivalTablistCloudProvider implements IListener {

    @CloudEventHandler
    public void onPermissionPlayerUpdated(PermissionPlayerUpdatedEvent rawEvent) {
        SurvivalTablist.setPlayerTeams();
    }

    @CloudEventHandler
    public void onCloudPlayerLogin(CloudPlayerLoginEvent rawEvent) {
        SurvivalTablist.setTablist();
    }

    @CloudEventHandler
    public void onCloudPlayerDisconnect(CloudPlayerDisconnectEvent rawEvent) {
        LobbyTablist.setTablist();
    }

}
