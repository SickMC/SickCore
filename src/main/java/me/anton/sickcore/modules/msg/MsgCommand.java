package me.anton.sickcore.modules.msg;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bungeePlayer.BungeePlayer;
import me.anton.sickcore.api.utils.minecraft.messages.ConsoleMessages;
import me.anton.sickcore.core.ProxyCore;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@CommandAlias("msg|message|tell|w")
public class MsgCommand extends BaseCommand {

    @Default
    @Syntax("<Player> <Message>")
    @Description("Sends the player a message")
    @CommandCompletion("@Players")
    public void onMsg(CommandSender commandSender, String name, String[] message){
        if (!(commandSender instanceof ProxiedPlayer)){
            ConsoleMessages.noPlayerBungee(commandSender);
            return;
        }
        BungeePlayer player = new BungeePlayer(commandSender);
        ProxiedPlayer targetproxied = ProxyCore.getInstance().getPlugin().getProxy().getPlayer(name);
        if (!targetproxied.isConnected()){
            player.sendMessage(LanguagePath.NETWORK_COMMAND_NOPLAYER);
            return;
        }
        BungeePlayer target = new BungeePlayer(targetproxied);
        String formatted = String.join(" ", message);
        player.getPlayer().sendMessage(new TextComponent("§7To §6" + name + "§8 » §7" + formatted));
        target.getPlayer().sendMessage(new TextComponent("§7From §6" + player.api().getName() + "§8 » §7" + formatted));
    }

}
