package me.anton.sickcore.gameapi.tablist;

import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.utils.common.ColorUtils;
import me.anton.sickcore.gameapi.AbstractGame;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Tablist {

    public AbstractGame game;

    public Tablist(AbstractGame game){
        this.game = game;
    }

    public void reload() {
        for (Player player : Bukkit.getOnlinePlayers()) {

            IBukkitPlayer bukkitPlayer = new BukkitPlayer(player);
            TabData data = new TabData(bukkitPlayer);

            player.sendPlayerListHeaderAndFooter(Component.text(data.getHeader()), Component.text(data.getFooter()));

            Scoreboard scoreboard = player.getScoreboard();

            if (bukkitPlayer.isNicked()) {
                if (scoreboard.getTeam(data.getNickedteamName()) == null)
                    scoreboard.registerNewTeam(data.getNickedteamName());
                Team team = scoreboard.getTeam(data.getNickedteamName());
                team.prefix(Component.text(bukkitPlayer.api().getNickRank().getColor() + bukkitPlayer.api().getNickRank().getName() + "§8 × §r" + bukkitPlayer.api().getNickRank().getColor()));
                team.color(ColorUtils.namedTextColorByChar(bukkitPlayer.api().getNickRank().getColor()));
                team.suffix(Component.text("§7"));
                player.displayName(Component.text(data.getNickedDisplayname()));
                team.addEntry(bukkitPlayer.api().getNickname());
            } else {
                if (scoreboard.getTeam(data.getTeamName()) == null) scoreboard.registerNewTeam(data.getTeamName());
                Team team = scoreboard.getTeam(data.getTeamName());
                team.prefix(Component.text(bukkitPlayer.api().getRank().getColor() + bukkitPlayer.api().getRank().getName() + "§8 × §r" + bukkitPlayer.api().getRankColor()));
                team.color(ColorUtils.namedTextColorByChar(bukkitPlayer.api().getRankColor()));
                team.suffix(Component.text("§7"));
                player.displayName(Component.text(data.getDisplayname()));
                team.addPlayer(bukkitPlayer.getPlayer());
            }
        }
    }
}