package me.anton.sickcore.api.utils.minecraft.messages;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;

public class ConsoleMessages {

    public static void noPlayer(CommandSender sender){ sender.sendMessage("§4This is a player only command!");}

    public static void noPlayerBungee(net.md_5.bungee.api.CommandSender sender){ sender.sendMessage(new TextComponent("§4This is a player only command!"));}

}
