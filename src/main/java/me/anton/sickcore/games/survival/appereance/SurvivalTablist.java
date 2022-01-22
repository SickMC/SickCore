package me.anton.sickcore.games.survival.appereance;

import eu.thesimplecloud.api.CloudAPI;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.apiPlayer.enums.Rank;
import me.anton.sickcore.api.player.apiPlayer.enums.RankBridge;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import xyz.haoshoku.nick.events.NickFinishEvent;

public class SurvivalTablist extends BukkitHandler {

    public static void setTablist(){
        Bukkit.getOnlinePlayers().forEach(player -> {
            IAPIPlayer iapiPlayer = new APIPlayer(player.getUniqueId());
            int online = CloudAPI.getInstance().getCloudPlayerManager().getNetworkOnlinePlayerCount().getBlocking();
            player.sendPlayerListHeaderAndFooter(Component.text("§8§m                   §r§8[ §6Survival§8 ]§m                   "
                    + iapiPlayer.languageString("\n§7Welcome on §6SickMC§7!\n", "\n§7Willkommen auf §6SickMC§7!\n") + "§7Online: §6"
                    + online + "\n"), Component.text("§8§m\n               §r§8[ §6play.sickmc.net§8 ]§m               §8"));
        });
    }

    public static void setPlayerTeams(){
        Bukkit.getOnlinePlayers().forEach(player -> {
            Scoreboard scoreboard = player.getScoreboard();
            IAPIPlayer iapiPlayer = new APIPlayer(player.getUniqueId());

            String criteria = null;
            if (iapiPlayer.bukkit().isNicked()){
                switch (iapiPlayer.getNickRank()){
                    case PLAYER -> criteria = "010playersn";
                    case VIP -> criteria = "007vipsn";
                    case MVP -> criteria = "006mvpsn";
                    case CONTENT -> criteria = "005contentn";
                    case BUILDER -> criteria = "004buildern";
                    case MODERATOR -> criteria = "002modsn";
                    case ADMIN -> criteria = "001adminsn";
                    case DEV -> criteria = "003devsn";
                }
            }else {
                if (iapiPlayer.getRank().equals(Rank.PLAYER))criteria = "010players";
                if (iapiPlayer.getRank().equals(Rank.VIP))criteria = "007vips";
                if (iapiPlayer.getRank().equals(Rank.MVP))criteria = "006mvps";
                if (iapiPlayer.getRank().equals(Rank.BUILDER))criteria = "005content";
                if (iapiPlayer.getRank().equals(Rank.CONTENT))criteria = "004builder";
                if (iapiPlayer.getRank().equals(Rank.DEV))criteria = "003devs";
                if (iapiPlayer.getRank().equals(Rank.MODERATOR))criteria = "002mods";
                if (iapiPlayer.getRank().equals(Rank.ADMIN))criteria = "001admins";
            }

            if (scoreboard.getTeam(criteria + player.getName()) == null)scoreboard.registerNewTeam(criteria + player.getName());
            Team team = scoreboard.getTeam(criteria + player.getName());
            team.prefix(Component.text(iapiPlayer.getNickRank().getFullPrefix() + "§" + RankBridge.getColor(iapiPlayer.getNickRank()).getChar()));
            team.setColor(RankBridge.getColor(iapiPlayer.getNickRank()));
            team.suffix(Component.text("§" + RankBridge.getColor(iapiPlayer.getNickRank()).getChar()));
            team.addPlayer(player);
            team.displayName(Component.text(iapiPlayer.bukkit().getNickedPrefix()));
        });
    }

    @Override
    public void onPlayerJoin(PlayerJoinEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        setPlayerTeams();
        setTablist();
    }

    @Override
    public void onPlayerNickFinish(NickFinishEvent rawEvent, IBukkitPlayer bukkitPlayer, String nickname) {
        setPlayerTeams();
    }
}
