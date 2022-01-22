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

@CommandAlias("unvanish")
public class UnVanishCommand extends BaseCommand {

    @Default
    @Description("Unvanish a player")
    public void onVanish(CommandSender sender){
        if (!(sender instanceof Player)){
            ConsoleMessages.noPlayer(sender);
            return;
        }

        IBukkitPlayer player = new BukkitPlayer((Player)sender);

        if (!player.api().isTeam()){player.message().noTeam();return;}
        if (!VanishListInventory.vanishlist.contains(player.getPlayer())){player.sendMessage("§4You are already unvanished!","Du bist bereits sichtbar!");return;}

        player.unVanish();
        player.sendMessage("§7You are now visible!", "§7Du bist nun sichtbar!");
    }


}
