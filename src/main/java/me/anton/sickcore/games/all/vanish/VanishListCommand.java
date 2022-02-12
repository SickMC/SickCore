package me.anton.sickcore.games.all.vanish;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.messages.ConsoleMessages;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("vanishlist|vl")
public class VanishListCommand extends BaseCommand {
    @Default
    @Description("Opens the vanishlist gui")
    public void onCommand(CommandSender sender){
        if (!(sender instanceof Player)){
            ConsoleMessages.noPlayer(sender);
            return;
        }
        IBukkitPlayer player = new BukkitPlayer(sender);

        if (!player.api().isTeam()){player.sendMessage(LanguagePath.NETWORK_COMMAND_NOSTAFF);return;}

        if (BukkitCore.getInstance().bukkit().getVanished().isEmpty()){player.getPlayer().sendMessage((String) player.api().languageObject("§7No player is vanished!", "§7Es ist gerade kein Player gevanished!")); return;}

        VanishListInventory.openInventory(new BukkitPlayer(sender));
    }

}
