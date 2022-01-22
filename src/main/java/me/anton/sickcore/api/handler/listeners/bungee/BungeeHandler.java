package me.anton.sickcore.api.handler.listeners.bungee;

import me.anton.sickcore.api.player.bungeePlayer.IBungeePlayer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.*;

public class BungeeHandler {

    public void onPlayerJoin(LoginEvent rawEvent){}

    public void onPostJoin(PostLoginEvent rawEvent, IBungeePlayer player){}

    public void onServerConnected(ServerConnectedEvent rawEvent, IBungeePlayer player){}

    public void onServerConnect(ServerConnectEvent rawEvent, IBungeePlayer player){}

    public void onServerSwitch(ServerSwitchEvent rawEvent, IBungeePlayer player){}

    public void onDisconnect(PlayerDisconnectEvent rawEvent, IBungeePlayer player){}

    public void onPing(ProxyPingEvent rawEvent, PendingConnection pendingConnection, ServerPing serverPing){}

    public void onChat(ChatEvent rawEvent,String message, IBungeePlayer player){}
}
