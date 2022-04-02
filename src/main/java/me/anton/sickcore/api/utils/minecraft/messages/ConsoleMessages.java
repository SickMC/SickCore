package me.anton.sickcore.api.utils.minecraft.messages;

import com.velocitypowered.api.command.CommandSource;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;

public class ConsoleMessages {

    public static void noPlayer(CommandSender sender){ sender.sendMessage("§4This is a player only command!");}

    public static void noPlayerBungee(CommandSource sender){ sender.sendMessage(Component.text("§4This is a player only command!"));}

}
