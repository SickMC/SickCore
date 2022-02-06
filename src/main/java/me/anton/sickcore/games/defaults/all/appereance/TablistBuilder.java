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
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TablistBuilder{

    private final String title;
    private final boolean nick;
    private final Document config;

    public TablistBuilder(String header, boolean nick){
        DatabaseModel model = Core.getInstance().getAppereanceModel();
        this.config = model.getDocument(Finder.stringFinder("type", "tablist"));
        this.title = header;
        this.nick = nick;
        BukkitCore.getInstance().getProvider().register(new TablistBuilderProvider(this));
    }

    public void setTablist(){
        Bukkit.getOnlinePlayers().forEach(player -> {
            IAPIPlayer iapiPlayer = new APIPlayer(player.getUniqueId());
            int online = CloudAPI.getInstance().getCloudPlayerManager().getNetworkOnlinePlayerCount().getBlocking();

            String headeren = config.getString("header").replace("%online%", String.valueOf(online)).replace("%n", "\n").replace("%modi%", title).replace("%server%", CloudAPI.getInstance().getThisSidesName());
            String headerde = config.getString("headerde").replace("%online%", String.valueOf(online)).replace("%n", "\n").replace("%modi%", title).replace("%server%", CloudAPI.getInstance().getThisSidesName());
            String footeren = config.getString("footer").replace("%online%", String.valueOf(online)).replace("%n", "\n").replace("%modi%", title).replace("%server%", CloudAPI.getInstance().getThisSidesName());
            String footerde = config.getString("footerde").replace("%online%", String.valueOf(online)).replace("%n", "\n").replace("%modi%", title).replace("%server%", CloudAPI.getInstance().getThisSidesName());

            player.sendPlayerListHeaderAndFooter(Component.text((String) iapiPlayer.languageObject(headeren, headerde)), Component.text((String) iapiPlayer.languageObject(footeren, footerde)));
        });
    }

    public void assignTeams(){
        Bukkit.getOnlinePlayers().forEach(player -> {
            IAPIPlayer iapiPlayer = new APIPlayer(player.getUniqueId());
            Scoreboard scoreboard = player.getScoreboard();
            if (!nick) {
                String criteria = iapiPlayer.getRank().getPriority() + iapiPlayer.getRank().getName();

                if (scoreboard.getTeam(criteria + iapiPlayer.getName()) == null)scoreboard.registerNewTeam(criteria + iapiPlayer.getName());
                Team team = scoreboard.getTeam(criteria + iapiPlayer.getName());
                team.prefix(Component.text("§" + ColorUtils.toChatColor(iapiPlayer.getRank().getColor()).getChar() + iapiPlayer.getRank().getName() + "§8 × §r"));
                team.color(ColorUtils.toNamedTextColor(iapiPlayer.getRankColor()));
                team.suffix(Component.text("§r"));
                team.addPlayer(player);
                team.displayName(Component.text(iapiPlayer.getDisplayName()));
            }else {
                String criteria = iapiPlayer.getNickRank().getPriority() + iapiPlayer.getNickRank().getName() + "n";
                if (scoreboard.getTeam(criteria + iapiPlayer.getName()) == null)scoreboard.registerNewTeam(criteria + iapiPlayer.getName());
                Team team = scoreboard.getTeam(criteria + iapiPlayer.getName());
                team.prefix(Component.text("§" + ColorUtils.toChatColor(iapiPlayer.getNickRank().getColor()).getChar() + iapiPlayer.getNickRank().getName() + "§8 × §r"));
                team.color(ColorUtils.toNamedTextColor(iapiPlayer.getNickRank().getColor()));
                team.suffix(Component.text("§r"));
                team.addPlayer(player);
                team.displayName(Component.text(iapiPlayer.bukkit().getNickedDisplayName()));
            }
        });

    }

    public boolean isNick() {
        return nick;
    }

}
