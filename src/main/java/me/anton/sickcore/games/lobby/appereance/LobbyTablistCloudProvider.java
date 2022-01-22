package me.anton.sickcore.games.lobby.appereance;

import eu.thesimplecloud.api.event.player.CloudPlayerDisconnectEvent;
import eu.thesimplecloud.api.event.player.CloudPlayerLoginEvent;
import eu.thesimplecloud.api.eventapi.CloudEventHandler;
import eu.thesimplecloud.api.eventapi.IListener;
import eu.thesimplecloud.module.permission.event.player.PermissionPlayerUpdatedEvent;

public class LobbyTablistCloudProvider implements IListener {

    @CloudEventHandler
    public void handle(PermissionPlayerUpdatedEvent rawEvent) {
        LobbyTablist.setPlayerTeams();
    }

    @CloudEventHandler
    public void handle(CloudPlayerLoginEvent rawEvent) {
        LobbyTablist.setTablist();
    }

    @CloudEventHandler
    public void handle(CloudPlayerDisconnectEvent rawEvent) {
        LobbyTablist.setTablist();
    }
}
