package me.anton.sickcore.games.survival;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.messages.ConsoleMessages;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("ecsee|enderchestsee")
public class EnderchestSee extends BaseCommand {

    @Default
    @CommandCompletion("@Players")
    @Syntax("<Player>")
    @Description("Shows the enderchest of the player")
    public void onSee(CommandSender commandSender, String targetName){
        if (!(commandSender instanceof Player)) {
            ConsoleMessages.noPlayer(commandSender);
            return;
        }
        Player player = (Player) commandSender;
        BukkitPlayer bukkitPlayer = new BukkitPlayer(player);

        if (!bukkitPlayer.api().isMod()){
            bukkitPlayer.sendMessage(LanguagePath.NETWORK_COMMAND_NOMOD);
            return;
        }

        OfflinePlayer target = Bukkit.getPlayerExact(targetName);
        if (target == null){
            bukkitPlayer.sendMessage(LanguagePath.NETWORK_COMMAND_NOPLAYER);
            return;
        }
        player.openInventory(target.getPlayer().getEnderChest());
    }

    @HelpCommand
    @Subcommand("help")
    @Description("Erhalte eine Hilfe")
    public static void onHelp(CommandHelp commandHelp){ commandHelp.showHelp();}

}
