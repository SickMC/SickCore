package me.anton.sickcore.gameapi.tablist;

import de.dytanic.cloudnet.driver.event.EventListener;
import de.dytanic.cloudnet.ext.bridge.event.BridgeProxyPlayerDisconnectEvent;
import de.dytanic.cloudnet.ext.bridge.event.BridgeProxyPlayerLoginSuccessEvent;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.haoshoku.nick.events.NickFinishEvent;

public class TablistProvider extends BukkitHandler {

    @EventListener
    public void handle(BridgeProxyPlayerLoginSuccessEvent rawEvent){
        BukkitCore.getInstance().getCurrentGame().getTablist().reload();
    }

    @EventListener
    public void handle(BridgeProxyPlayerDisconnectEvent rawEvent) {
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
