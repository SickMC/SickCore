package me.anton.sickcore.games.defaults.all.appereance;

import eu.thesimplecloud.api.event.player.CloudPlayerDisconnectEvent;
import eu.thesimplecloud.api.event.player.CloudPlayerLoginEvent;
import eu.thesimplecloud.api.eventapi.CloudEventHandler;
import eu.thesimplecloud.api.eventapi.IListener;
import eu.thesimplecloud.module.permission.event.player.PermissionPlayerUpdatedEvent;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.haoshoku.nick.events.NickFinishEvent;

public class TablistBuilderProvider extends BukkitHandler implements IListener {

    TablistBuilder builder;

    public TablistBuilderProvider(TablistBuilder builder){
        this.builder = builder;
    }

    @CloudEventHandler
    public void handle(PermissionPlayerUpdatedEvent rawEvent) {
        builder.assignTeams();
    }

    @CloudEventHandler
    public void handle(CloudPlayerLoginEvent rawEvent) {
        builder.assignTeams();
    }

    @CloudEventHandler
    public void handle(CloudPlayerDisconnectEvent rawEvent) {
        builder.assignTeams();
    }

    @Override
    public void onPlayerNickFinish(NickFinishEvent rawEvent, IBukkitPlayer bukkitPlayer, String nickname) {
        if (!builder.isNick()){
            bukkitPlayer.unnick();
        }else{
            builder.assignTeams();
        }
    }

    @Override
    public void onPlayerJoin(PlayerJoinEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        builder.assignTeams();
        builder.setTablist();
    }

    @Override
    public void onPlayerQuit(PlayerQuitEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        builder.setTablist();
        builder.assignTeams();
    }
}
