package me.anton.sickcore.modules.motd;

import me.anton.sickcore.api.handler.listeners.bungee.BungeeHandler;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.ProxyPingEvent;

import java.util.UUID;

public class MOTDHandler extends BungeeHandler {

    @Override
    public void onPing(ProxyPingEvent rawEvent, PendingConnection pendingConnection, ServerPing serverPing) {
        serverPing.setDescriptionComponent(new TextComponent("§6      SickMC §7Network - §6play.sickmc.net §7- §8[§f1.18.1§8]      " +
                "\n            §7§kz §r§7German Minecraft Network§7 §kz            §7"));

        ServerPing.PlayerInfo playerInfo = new ServerPing.PlayerInfo("§7Discord: §6discord.sickmc.net", UUID.randomUUID());

        ServerPing.PlayerInfo[] playerInfos = {playerInfo};

        ServerPing.Players players = serverPing.getPlayers();
        players.setSample(playerInfos);

        serverPing.setPlayers(players);

        rawEvent.setResponse(serverPing);
    }
}
