package me.anton.sickcore.api.handler.listeners.bungee;

import me.anton.sickcore.api.player.bungeePlayer.BungeePlayer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.*;

public class BungeeHandler {

    public void onPlayerJoin(LoginEvent rawEvent){}

    public void onPostJoin(PostLoginEvent rawEvent, BungeePlayer player){}

    public void onServerConnected(ServerConnectedEvent rawEvent, BungeePlayer player){}

    public void onServerConnect(ServerConnectEvent rawEvent, BungeePlayer player){}

    public void onServerSwitch(ServerSwitchEvent rawEvent, BungeePlayer player){}

    public void onDisconnect(PlayerDisconnectEvent rawEvent, BungeePlayer player){}

    public void onPing(ProxyPingEvent rawEvent, PendingConnection pendingConnection, ServerPing serverPing){}

    public void onChat(ChatEvent rawEvent,String message, BungeePlayer player){}
}
