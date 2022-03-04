package me.anton.sickcore.modules.motd;

import me.anton.sickcore.api.database.Finder;
import me.anton.sickcore.api.handler.listeners.bungee.BungeeHandler;
import me.anton.sickcore.api.utils.common.StringUtils;
import me.anton.sickcore.core.Core;
import me.anton.sickcore.gameapi.ConfigurationHandler;
import me.anton.sickcore.games.all.maintenance.MaintenanceModule;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.ProxyPingEvent;
import org.bson.Document;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MOTDHandler extends BungeeHandler {

    private Document document = MOTDModule.getInstance().getConfig().getDocument();

    @Override
    public void onPing(ProxyPingEvent rawEvent, PendingConnection pendingConnection, ServerPing serverPing) {
        //MOTD
        String firstLine = StringUtils.centerText(document.getString("firstLine"), 65);
        String secondLine = StringUtils.centerText(document.getString("secondLine"), 65);

        BaseComponent component = new TextComponent(firstLine + "\n" + secondLine);

        serverPing.setDescriptionComponent(component);

        //PlayerInfo
        List<ServerPing.PlayerInfo> playerInfoList = new ArrayList<>();

        ServerPing.PlayerInfo first = new ServerPing.PlayerInfo(getPlayerInfo("firstPlayerInfo"), UUID.randomUUID());
        playerInfoList.add(first);

        if (!(getPlayerInfo("secondPlayerInfo") == null)){
            ServerPing.PlayerInfo second = new ServerPing.PlayerInfo(getPlayerInfo("secondPlayerInfo"), UUID.randomUUID());
            playerInfoList.add(second);
        }

        if (!(getPlayerInfo("thirtPlayerInfo") == null)){
            ServerPing.PlayerInfo thirt = new ServerPing.PlayerInfo(getPlayerInfo("thirtPlayerInfo"), UUID.randomUUID());
            playerInfoList.add(thirt);
        }

        ServerPing.PlayerInfo[] playerInfos = playerInfoList.toArray(new ServerPing.PlayerInfo[0]);

        ServerPing.Players players = serverPing.getPlayers();
        players.setSample(playerInfos);

        serverPing.setPlayers(players);

        Document maintenance = ConfigurationHandler.getMaintenanceConfig().getDocument();

        if (maintenance.getBoolean("active"))
            serverPing.setVersion(new ServerPing.Protocol("§7Maintenance", serverPing.getVersion().getProtocol()));

        rawEvent.setResponse(serverPing);
    }

    @Nullable
    private String getPlayerInfo(String key){
        if (document.getString(key).equals("0"))return null;
        else return document.getString(key);
    }

}
