package me.anton.sickcore.games.defaults.all;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.apiPlayer.language.LanguageObject;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.utils.common.Replacable;
import me.anton.sickcore.api.utils.common.MathUtils;
import me.anton.sickcore.api.utils.minecraft.messages.ConsoleMessages;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("tps|lag")
public class LagCommand extends BaseCommand {

    @Default
    @Description("Gives information about the server")
    public void onCommand(CommandSender sender) {
        if (!(sender instanceof Player)){
            ConsoleMessages.noPlayer(sender);
            return;
        }

        Player player = (Player) sender;
        IAPIPlayer iapiPlayer = new APIPlayer(player.getUniqueId());

        int averageTPS = 0;
        for (Player all : Bukkit.getOnlinePlayers())
            averageTPS = averageTPS + all.getPing();

        averageTPS = averageTPS/Bukkit.getOnlinePlayers().size();

        iapiPlayer.bukkit().sendMessage(new LanguageObject(iapiPlayer ,LanguagePath.PAPER_COMMAND_LAGCOMMAND_TIMINGS)
                .replace(new Replacable("%tps%",String.valueOf(Math.round( Bukkit.getServer().getTPS()[0]))),
                        new Replacable("%yourPing%", String.valueOf(MathUtils.round(player.getPing()))),
                        new Replacable("%averagePing%", String.valueOf(MathUtils.round(averageTPS)))));
    }

}
