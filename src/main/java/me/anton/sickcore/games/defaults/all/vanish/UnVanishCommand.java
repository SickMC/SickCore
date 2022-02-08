package me.anton.sickcore.games.defaults.all.vanish;

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

        if (!player.api().isTeam()){player.sendMessage(LanguagePath.NETWORK_COMMAND_NOSTAFF);return;}
        if (!BukkitCore.getInstance().bukkit().getVanished().contains(player.getPlayer())){
            player.getPlayer().sendMessage((String) player.api().languageObject("§4You are already unvanished!","Du bist bereits sichtbar!"));return;
        }

        player.unVanish();
        player.getPlayer().sendMessage((String) player.api().languageObject("§7You are now visible!", "§7Du bist nun sichtbar!"));
    }


}
