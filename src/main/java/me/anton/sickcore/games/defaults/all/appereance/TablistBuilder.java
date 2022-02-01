package me.anton.sickcore.games.defaults.all.appereance;

import eu.thesimplecloud.api.CloudAPI;
import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.api.database.Finder;
import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.utils.common.ColorUtils;
import me.anton.sickcore.core.BukkitCore;
import me.anton.sickcore.core.Core;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TablistBuilder extends ITablistBuilder{

    private final String title;
    private final boolean nick;
    private final DatabaseModel model;
    private final Document config;

    public TablistBuilder(String header, boolean nick){
        BukkitCore.getInstance().getProvider().register(new TablistBuilderProvider());
        this.model = Core.getInstance().getAppereanceModel();
        this.config = model.getDocument(Finder.stringFinder("type", "tablist"));
        this.title = header;
        this.nick = nick;
    }

    public void setTablist(){
        Bukkit.getOnlinePlayers().forEach(player -> {
            IAPIPlayer iapiPlayer = new APIPlayer(player.getUniqueId());
            int online = CloudAPI.getInstance().getCloudPlayerManager().getNetworkOnlinePlayerCount().getBlocking();

            String headeren = config.getString("header");
            String headerde = config.getString("headerde");
            String footeren = config.getString("footer");
            String footerde = config.getString("footerde");

            String[] handlers = {headerde, headeren, footerde, footeren};
            for (String handler : handlers) {
                handler.replace("%online%", String.valueOf(online));
                handler.replace("%n", "\n");
                handler.replace("%modi%", title);
                handler.replace("%server%", CloudAPI.getInstance().getThisSidesName());
            }

            player.sendPlayerListHeaderAndFooter(Component.text(iapiPlayer.languageString(headeren, headerde)), Component.text(iapiPlayer.languageString(footeren, footerde)));
        });
    }

    public void assignTeams(){
        Bukkit.getOnlinePlayers().forEach(player -> {
            IAPIPlayer iapiPlayer = new APIPlayer(player.getUniqueId());
            Scoreboard scoreboard = player.getScoreboard();
            if (!nick) {
                String criteria = iapiPlayer.getRank().getPriority() + iapiPlayer.getRank().getRawPrefix();

                if (scoreboard.getTeam(criteria + iapiPlayer.getName()) == null)scoreboard.registerNewTeam(criteria + iapiPlayer.getName());
                Team team = scoreboard.getTeam(criteria + iapiPlayer.getName());
                team.prefix(Component.text(iapiPlayer.getRank().getPrefix()));
                team.color(ColorUtils.toNamedTextColor(iapiPlayer.getRankColor()));
                team.suffix(Component.text("§r"));
                team.addPlayer(player);
                team.displayName(Component.text(iapiPlayer.getDisplayName()));
            }else {
                String criteria = iapiPlayer.getNickRank().getPriority() + iapiPlayer.getNickRank().getRawPrefix() + "n";
                if (scoreboard.getTeam(criteria + iapiPlayer.getName()) == null)scoreboard.registerNewTeam(criteria + iapiPlayer.getName());
                Team team = scoreboard.getTeam(criteria + iapiPlayer.getName());
                team.prefix(Component.text(iapiPlayer.getNickRank().getPrefix()));
                team.color(ColorUtils.toNamedTextColor(iapiPlayer.getNickRank().getColor()));
                team.suffix(Component.text("§r"));
                team.addPlayer(player);
                team.displayName(Component.text(iapiPlayer.bukkit().getNickedPrefix()));
            }
        });

    }

    public boolean isNick() {
        return nick;
    }

}
