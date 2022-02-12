package me.anton.sickcore.games.all.nick;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.messages.ConsoleMessages;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

@CommandAlias("nicklist|nl")
public class NickListCommand extends BaseCommand {

    @Default
    @Description("Opens the nicklist gui")
    public void onCommand(CommandSender sender){
        if (!(sender instanceof Player)){
            ConsoleMessages.noPlayer(sender);
            return;
        }

        HashMap<Player, String> nicklist = new HashMap<>();
        IBukkitPlayer player = new BukkitPlayer(sender);

        if (!player.api().isTeam()){player.sendMessage(LanguagePath.NETWORK_COMMAND_NOSTAFF);return;}

        player.getPlayer().getServer().getOnlinePlayers().forEach(onlinePlayer -> {
            IBukkitPlayer online = new BukkitPlayer(onlinePlayer);
            if (!online.isNicked()) return;
            nicklist.put(onlinePlayer, online.getName());
        });
        if (nicklist.isEmpty()){player.getPlayer().sendMessage((String) player.api().languageObject("§7No player is nicked!", "§7Es ist gerade kein Player genickt!")); return;}

        NickListInventory.openInventory(new BukkitPlayer(sender));
    }

}
