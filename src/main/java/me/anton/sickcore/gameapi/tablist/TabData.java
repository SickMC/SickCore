package me.anton.sickcore.gameapi.tablist;

import eu.thesimplecloud.api.CloudAPI;
import lombok.Getter;
import me.anton.sickcore.api.database.Finder;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import me.anton.sickcore.core.Core;
import org.bson.Document;

@Getter
public class TabData {

    private Document config = BukkitCore.getInstance().getCurrentGame().getTablist().getConfig().getDocument();
    private int online = CloudAPI.getInstance().getCloudPlayerManager().getNetworkOnlinePlayerCount().getBlocking();
    private String title = BukkitCore.getInstance().getCurrentGame().getName();

    public final BukkitPlayer player;
    public final String displayname;
    public final String nickedDisplayname;
    public final String header;
    public final String footer;
    public final String criteria;
    public final String nickedCriteria;
    public final String teamName;
    public final String nickedteamName;

    private String headeren = config.getString("header").replace("%online%", String.valueOf(online)).replace("%n", "\n").replace("%modi%", title).replace("%server%", CloudAPI.getInstance().getThisSidesName());
    private String headerde = config.getString("headerde").replace("%online%", String.valueOf(online)).replace("%n", "\n").replace("%modi%", title).replace("%server%", CloudAPI.getInstance().getThisSidesName());
    private String footeren = config.getString("footer").replace("%online%", String.valueOf(online)).replace("%n", "\n").replace("%modi%", title).replace("%server%", CloudAPI.getInstance().getThisSidesName());
    private String footerde = config.getString("footerde").replace("%online%", String.valueOf(online)).replace("%n", "\n").replace("%modi%", title).replace("%server%", CloudAPI.getInstance().getThisSidesName());

    public TabData(BukkitPlayer player){
        this.player = player;
        this.displayname = player.api().getRank().getColor() + player.api().getRank().getName() + "§8 × §r" + player.api().getRankColor() + player.api().getName() + "§r";
        this.nickedDisplayname = player.api().getNickRank().getColor() + player.api().getNickRank().getName() + "§8 × §r" + player.api().getNickRank().getColor() + player.api().getNickname() + "§r";
        this.header = (String) player.api().languageObject(headeren, headerde);
        this.footer = (String) player.api().languageObject(footeren, footerde);
        this.criteria = player.api().getRank().getPriority() + player.api().getRank().getName();
        this.nickedCriteria = player.api().getNickRank().getPriority() + player.api().getNickRank().getName();
        this.teamName = criteria + player.api().getName();
        this.nickedteamName = nickedCriteria + player.api().getNickname();
    }

}
