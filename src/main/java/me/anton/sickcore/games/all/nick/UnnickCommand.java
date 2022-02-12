package me.anton.sickcore.games.all.nick;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import me.anton.sickcore.api.player.apiPlayer.enums.Rank;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.messages.ConsoleMessages;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("unnick")
public class UnnickCommand extends BaseCommand {

    @Default
    @Description("Unnicks the player")
    public void onNick(CommandSender sender){
        if (!(sender instanceof Player)) {
            ConsoleMessages.noPlayer(sender);
            return;
        }

        IBukkitPlayer bukkitPlayer = new BukkitPlayer(((Player) sender).getUniqueId());
        if (!bukkitPlayer.api().isTeam() || bukkitPlayer.api().getRank() == Rank.MVP){bukkitPlayer.getPlayer().sendMessage((String) bukkitPlayer.api().languageObject("§4Available with MVP or higher!", "§4Verfügbar mit MVP oder höher!"));return;}
        if (!bukkitPlayer.isNicked()){bukkitPlayer.getPlayer().sendMessage((String) bukkitPlayer.api().languageObject("§4You are already unnicked!", "§4Du bist bereits ungenickt!")); return;}

        bukkitPlayer.unnick();
        bukkitPlayer.getPlayer().sendMessage((String) bukkitPlayer.api().languageObject("§7You are now unnicked!", "§7Du bist nun nicht mehr genickt!"));
    }

}