package me.anton.sickcore.modules.basic.buildserver;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import eu.thesimplecloud.api.CloudAPI;
import me.anton.sickcore.api.player.bungeePlayer.BungeePlayer;
import me.anton.sickcore.api.player.bungeePlayer.IBungeePlayer;
import me.anton.sickcore.api.utils.minecraft.messages.ConsoleMessages;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@CommandAlias("bs|buildserver")
public class BuildServerCommand extends BaseCommand {

    @Default
    public void onCMD(CommandSender sender){
        if (!(sender instanceof ProxiedPlayer)){
            ConsoleMessages.noPlayerBungee(sender);
            return;
        }

        IBungeePlayer player = new BungeePlayer(sender);
        if (!player.api().isTeam())return;

        if (player.api().cloud().cloudAPI().getConnectedServer().getName().startsWith("Build-"))player.sendMessage("§4You are already on the buildserver!", "§4Du bist bereits auf dem Buildserver!");
        player.api().cloud().cloudAPI().connect(CloudAPI.getInstance().getCloudServiceManager().getCloudServiceByName("Build-1"));
        player.sendMessage("§7You were send to the buildserver!", "§7Du wurdest zum Build-Server teleportiert!");
    }

}
