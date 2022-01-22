package me.anton.sickcore.games.defaults.all;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import eu.thesimplecloud.api.CloudAPI;
import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.utils.common.math.MathUtils;
import me.anton.sickcore.api.utils.minecraft.messages.ConsoleMessages;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("tps|lag")
public class LagCommand extends BaseCommand {

    @Default
    @Description("Gives information about the server")
    public void onCommand(CommandSender sender,@Optional String[] args) {
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

        iapiPlayer.bukkit().sendMessage("§7Timings of §6" + CloudAPI.getInstance().getThisSidesName() + "§7" +
                "\n\n§7TPS: §6" + Math.round( Bukkit.getServer().getTPS()[0]) + "§7" +
                "\n§7Your Ping: §6" + MathUtils.round(player.getPing()) + "§7" +
                "\n§7Average Ping: §6"  + MathUtils.round(averageTPS), "§7Timings von §6" + CloudAPI.getInstance().getThisSidesName() + "§7\n" +
                "\n\n§7TPS: §6" + MathUtils.round( Bukkit.getServer().getTPS()[0]) + "§7" +
                "\n§7Dein Ping: §6" + Math.round(player.getPing()) + "§7\n" +
                "\n§7Durchschnittlicher Ping: §6"  + MathUtils.round(averageTPS));

    }

    @HelpCommand
    @CatchUnknown
    public void onHelp(CommandHelp sender){
        sender.showHelp();
    }

}
