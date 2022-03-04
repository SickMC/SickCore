package me.anton.sickcore.gameapi.tablist;

import eu.thesimplecloud.api.event.player.CloudPlayerDisconnectEvent;
import eu.thesimplecloud.api.event.player.CloudPlayerLoginEvent;
import eu.thesimplecloud.api.eventapi.CloudEventHandler;
import eu.thesimplecloud.api.eventapi.IListener;
import eu.thesimplecloud.module.permission.event.player.PermissionPlayerUpdatedEvent;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.haoshoku.nick.events.NickFinishEvent;

public class TablistProvider extends BukkitHandler implements IListener {

    @CloudEventHandler
    public void handle(PermissionPlayerUpdatedEvent rawEvent) {
        BukkitCore.getInstance().getCurrentGame().getTablist().reload();
    }

    @CloudEventHandler
    public void handle(CloudPlayerLoginEvent rawEvent){
        BukkitCore.getInstance().getCurrentGame().getTablist().reload();
    }

    @CloudEventHandler
    public void handle(CloudPlayerDisconnectEvent rawEvent) {
        BukkitCore.getInstance().getCurrentGame().getTablist().reload();
    }

    @Override
    public void onPlayerNickFinish(NickFinishEvent rawEvent, BukkitPlayer bukkitPlayer, String nickname) {
        BukkitCore.getInstance().getCurrentGame().getTablist().reload();
    }

    @Override
    public void onPlayerJoin(PlayerJoinEvent rawEvent, BukkitPlayer bukkitPlayer) {
        BukkitCore.getInstance().getCurrentGame().getTablist().reload();
    }

    @Override
    public void onPlayerQuit(PlayerQuitEvent rawEvent, BukkitPlayer bukkitPlayer) {
        BukkitCore.getInstance().getCurrentGame().getTablist().reload();
    }
}
