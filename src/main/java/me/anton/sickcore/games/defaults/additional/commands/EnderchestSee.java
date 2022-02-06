package me.anton.sickcore.games.defaults.additional.commands;

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

@CommandAlias("ecsee|enderchestsee")
public class EnderchestSee extends BaseCommand {

    @CommandCompletion("@Players")
    @Syntax("<Player>")
    @Description("Shows the enderchest of the player")
    public void onSee(CommandSender commandSender, String targetName){
        if (!(commandSender instanceof Player)) {
            ConsoleMessages.noPlayer(commandSender);
            return;
        }
        Player player = (Player) commandSender;
        IBukkitPlayer bukkitPlayer = new BukkitPlayer(player);

        if (!commandSender.hasPermission("sicknetwork.command.enderchestsee")) bukkitPlayer.sendMessage(LanguagePath.NETWORK_COMMAND_NOPERMISSION);

        OfflinePlayer target = Bukkit.getPlayerExact(targetName);
        assert target != null;
        player.openInventory(target.getPlayer().getEnderChest());
    }

    @HelpCommand
    @CatchUnknown
    public void onHelp(CommandHelp commandHelp) {commandHelp.showHelp();}

}
