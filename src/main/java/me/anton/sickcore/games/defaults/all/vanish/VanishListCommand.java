package me.anton.sickcore.games.defaults.all.vanish;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.messages.ConsoleMessages;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@CommandAlias("vanishlist|vl")
public class VanishListCommand extends BaseCommand {

    JavaPlugin plugin;

    public VanishListCommand(JavaPlugin plugin){this.plugin = plugin;}

    @Default
    @Description("Opens the vanishlist gui")
    public void onCommand(CommandSender sender){
        if (!(sender instanceof Player)){
            ConsoleMessages.noPlayer(sender);
            return;
        }
        IBukkitPlayer player = new BukkitPlayer(sender);

        if (!player.api().isTeam()){player.message().noTeam();return;}

        if (VanishListInventory.vanishlist.isEmpty()){new BukkitPlayer(sender).sendMessage("§7No player is vanished!", "§7Es ist gerade kein Player gevanished!"); return;}

        VanishListInventory.openInventory(new BukkitPlayer(sender), plugin);
    }

}
