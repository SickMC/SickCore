package me.anton.sickcore.modules.notify.notifys;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import me.anton.sickcore.api.player.bungeePlayer.BungeePlayer;
import me.anton.sickcore.api.utils.minecraft.messages.ConsoleMessages;
import me.anton.sickcore.modules.notify.NotifyAction;
import me.anton.sickcore.modules.notify.NotifyType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@CommandAlias("tc|teamchat")
public class TeamChatNotify extends BaseCommand {

    @Default
    @Description("Sends a message to the whole team")
    public void onMsg(CommandSender commandSender, String[] messages){
        if (!(commandSender instanceof ProxiedPlayer)){
            ConsoleMessages.noPlayerBungee(commandSender);
            return;
        }
        BungeePlayer player = new BungeePlayer(commandSender);

        String formatted = String.join(" ", messages);
        String message = player.api().getDisplayName() + "§7(" +  player.api().cloud().cloudAPI().getConnectedServerName() + ")§8 » §7" + formatted;
        new NotifyAction(message, NotifyType.TEAMCHAT);
    }

    @HelpCommand
    public void onHelp(CommandHelp commandHelp){
        commandHelp.showHelp();
    }

}
