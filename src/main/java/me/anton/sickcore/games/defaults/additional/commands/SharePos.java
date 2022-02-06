package me.anton.sickcore.games.defaults.additional.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.messages.ConsoleMessages;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("sharepos|sp|shareposition")
public class SharePos extends BaseCommand {

    @Default
    @Description("Shares the position with all players")
    public void onDefault(CommandSender sender, @Optional String[] args){
        if (!(sender instanceof Player)) {
            ConsoleMessages.noPlayer(sender);
            return;
        }
        Player player = (Player) sender;
        IBukkitPlayer bukkitPlayer = new BukkitPlayer(player);

        long x = Math.round(player.getLocation().getX());
        long y = Math.round(player.getLocation().getY());
        long z = Math.round(player.getLocation().getZ());

        Bukkit.broadcast(Component.text("§6" + player.getName()
                + "§7: X: §7" + x
                + ", Y: " + y
                + ", Z: " + z
                + ", World: §7" + getWorld(player)));
    }

    @CommandCompletion("@displayedPlayers")
    @Syntax("<Player>")
    @Description("Shares the position with a player")
    public void onShare(CommandSender commandSender, String targetName){
        if (!(commandSender instanceof Player)) {
            ConsoleMessages.noPlayer(commandSender);
            return;
        }
        Player player = (Player) commandSender;
        IBukkitPlayer bukkitPlayer = new BukkitPlayer(player);

        long x = Math.round(player.getLocation().getX());
        long y = Math.round(player.getLocation().getY());
        long z = Math.round(player.getLocation().getZ());

        Player target = Bukkit.getPlayerExact(targetName);
        target.sendMessage(Component.text("§6" + player.getName()
                + "§7: X: §7" + x
                + ", Y: " + y
                + ", Z: " + z
                + ", World: §7" + getWorld(player)));
        player.sendMessage(Component.text("§6" + player.getName()
                + "§7: X: §7" + x
                + ", Y: " + y
                + ", Z: " + z
                + ", World: §7" + getWorld(player)));
    }

    @HelpCommand
    @CatchUnknown
    public void onHelp(CommandHelp help){
        help.showHelp();
    }


    public String getWorld(Player player){
        if (player.getWorld().getEnvironment().getId() == 0){
            return "Overworld";
        }else if(player.getWorld().getEnvironment().getId() == -1)
            return "Nether";
        else if (player.getWorld().getEnvironment().getId() == 1){
            return "End";
        }else
            return "Custom";
    }


}
