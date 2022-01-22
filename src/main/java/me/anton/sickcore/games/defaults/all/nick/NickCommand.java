package me.anton.sickcore.games.defaults.all.nick;

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

@CommandAlias("nick")
public class NickCommand extends BaseCommand {

    @Default
    @Description("Nicks the player")
    public void onNick(CommandSender sender){
        if (!(sender instanceof Player)) {
            ConsoleMessages.noPlayer(sender);
            return;
        }

        IBukkitPlayer bukkitPlayer = new BukkitPlayer(((Player) sender).getUniqueId());
        if (!bukkitPlayer.api().isTeam() || bukkitPlayer.api().getRank() == Rank.MVP){bukkitPlayer.sendMessage("§4Available with MVP or higher!", "§4Verfügbar mit MVP oder höher!");return;}
        if (bukkitPlayer.isNicked()){bukkitPlayer.sendMessage("§4You are already nicked!", "§4Du bist bereits genickt!"); return;}

        bukkitPlayer.nick();
        bukkitPlayer.sendMessage("§7You are now nicked as §6" + bukkitPlayer.getName() + "§7 !", "§7Du bist nun genickt als §6" + bukkitPlayer.getName() + "§7 !");
        bukkitPlayer.sendMessage("§7Your nickskin is by §6" +  bukkitPlayer.getNickSkinName() + "§7 !", "§7Dein NickSkin ist von §6" + bukkitPlayer.getNickSkinName() + "§7 !");
    }

}
