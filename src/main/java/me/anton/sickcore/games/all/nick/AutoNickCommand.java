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

@CommandAlias("autonick|an|nicksystem")
public class AutoNickCommand extends BaseCommand {

    @Default
    @Description("Opens the nicksystem inventory")
    public void onTrigger(CommandSender sender){
        if (!(sender instanceof Player)) {
            ConsoleMessages.noPlayer(sender);
            return;
        }

        IBukkitPlayer bukkitPlayer = new BukkitPlayer(((Player) sender).getUniqueId());
        if (!bukkitPlayer.api().isTeam() || bukkitPlayer.api().getRank() == Rank.MVP){bukkitPlayer.getPlayer().sendMessage((String) bukkitPlayer.api().languageObject("§4Available with MVP or higher!", "§4Verfügbar mit MVP oder höher!"));return;}

        AutoNickInventory.openAutoNickInventory(bukkitPlayer);
    }

}
