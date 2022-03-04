package me.anton.sickcore.games.all.maintenance;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.messages.ConsoleMessages;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("maintenance")
public class MaintenanceCommand extends BaseCommand {

    @Default
    public void onCmd(CommandSender sender){
        if (!(sender instanceof Player)){
            ConsoleMessages.noPlayer(sender);
            return;
        }

        BukkitPlayer bungeePlayer = new BukkitPlayer(sender);
        if (!bungeePlayer.api().isTeam())bungeePlayer.sendMessage(LanguagePath.NETWORK_COMMAND_NOSTAFF);

        new MaintenanceInventory().openInv(bungeePlayer);
    }

}
