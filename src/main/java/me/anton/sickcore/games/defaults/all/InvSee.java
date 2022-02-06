package me.anton.sickcore.games.defaults.all;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.messages.ConsoleMessages;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("invsee|inventorysee")
public class InvSee extends BaseCommand {

    @CommandCompletion("@displayedPlayers")
    @Syntax("<Player>")
    @Description("Shows the inventory of the player")
    public void onSee(CommandSender commandSender, String targetName){
        if (!(commandSender instanceof Player)) {
            ConsoleMessages.noPlayer(commandSender);
            return;
        }
        Player player = (Player) commandSender;
        IBukkitPlayer bukkitPlayer = new BukkitPlayer(player);

        if (!commandSender.hasPermission("sicknetwork.command.invsee")) bukkitPlayer.sendMessage(LanguagePath.NETWORK_COMMAND_NOSTAFF);

        OfflinePlayer target = Bukkit.getPlayerExact(targetName);
        player.openInventory(target.getPlayer().getInventory());
    }
}
