package me.anton.sickcore.games.lobby.appereance;

import eu.thesimplecloud.api.CloudAPI;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.apiPlayer.enums.Rank;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import xyz.haoshoku.nick.events.NickFinishEvent;

public class LobbyTablist extends BukkitHandler {

    public static void setTablist(){
        Bukkit.getOnlinePlayers().forEach(player -> {
            IAPIPlayer iapiPlayer = new APIPlayer(player.getUniqueId());
            int online = CloudAPI.getInstance().getCloudPlayerManager().getNetworkOnlinePlayerCount().getBlocking();
            player.sendPlayerListHeaderAndFooter(Component.text("§8§m                   §r§8[ §6Lobby§8 ]§m                   "
                    + iapiPlayer.languageString("\n§7Welcome on §6SickMC§7!\n", "\n§7Willkommen auf §6SickMC§7!\n") + "§7Online: §6"
                    + online + "\n"), Component.text("§8§m\n               §r§8[ §6play.sickmc.net§8 ]§m               §8"));
        });
    }

    public static void setPlayerTeams(){
        Bukkit.getOnlinePlayers().forEach(player -> {
            Scoreboard scoreboard = player.getScoreboard();
            IAPIPlayer iapiPlayer = new APIPlayer(player.getUniqueId());

            String criteria = null;
            if (iapiPlayer.getRank().equals(Rank.PLAYER))criteria = "010players";
            if (iapiPlayer.getRank().equals(Rank.VIP))criteria = "007vips";
            if (iapiPlayer.getRank().equals(Rank.MVP))criteria = "006mvps";
            if (iapiPlayer.getRank().equals(Rank.CONTENT))criteria = "005content";
            if (iapiPlayer.getRank().equals(Rank.BUILDER))criteria = "004builder";
            if (iapiPlayer.getRank().equals(Rank.DEV))criteria = "003devs";
            if (iapiPlayer.getRank().equals(Rank.MODERATOR))criteria = "002mods";
            if (iapiPlayer.getRank().equals(Rank.ADMIN))criteria = "001admins";

            if (scoreboard.getTeam(criteria + player.getName()) == null)scoreboard.registerNewTeam(criteria + player.getName());
            Team team = scoreboard.getTeam(criteria + player.getName());
            team.prefix(Component.text(iapiPlayer.getRank().getFullPrefix() + "§" + iapiPlayer.getRankColor().getChar()));
            team.setColor(iapiPlayer.getRankColor());
            team.suffix(Component.text("§" + iapiPlayer.getDefaultRankColor().getChar()));
            team.addPlayer(player);
            team.displayName(Component.text(iapiPlayer.getDisplayName()));
        });
    }

    @Override
    public void onPlayerJoin(PlayerJoinEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        setPlayerTeams();
        setTablist();
    }

    @Override
    public void onPlayerNickFinish(NickFinishEvent rawEvent, IBukkitPlayer bukkitPlayer, String nickname) {
        bukkitPlayer.unnick();
    }

}
