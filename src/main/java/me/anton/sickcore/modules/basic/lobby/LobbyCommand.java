package me.anton.sickcore.modules.basic.lobby;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bungeePlayer.BungeePlayer;
import me.anton.sickcore.api.player.bungeePlayer.IBungeePlayer;
import me.anton.sickcore.api.utils.minecraft.messages.ConsoleMessages;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@CommandAlias("l|lobby|leave|hub")
public class LobbyCommand extends BaseCommand {

    @Default
    @Description("Sends you to lobby")
    public void onCOm(CommandSender sender){
        if (!(sender instanceof ProxiedPlayer)){
            ConsoleMessages.noPlayerBungee(sender);
            return;
        }

        IBungeePlayer player = new BungeePlayer(sender);
        if (player.api().cloud().cloudAPI().getConnectedServer().isLobby())player.sendMessage(LanguagePath.PROXY_STAFF_COMMAND_LOBBY_ALREADY);
        player.api().cloud().cloudAPI().sendToLobby();
        player.sendMessage(LanguagePath.PROXY_STAFF_COMMAND_LOBBY_SUCCESS);
    }

}
