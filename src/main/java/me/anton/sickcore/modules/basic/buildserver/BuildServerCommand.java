package me.anton.sickcore.modules.basic.buildserver;

import co.aikar.commands.annotation.CommandAlias;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import de.dytanic.cloudnet.ext.bridge.player.executor.ServerSelectorType;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bungeePlayer.BungeePlayer;

@CommandAlias("bs")
public class BuildServerCommand  {

    public void onCMD(){
        LiteralCommandNode<CommandSource> node = LiteralArgumentBuilder
                .<CommandSource>literal("bs")
                .executes(context -> {
                    if (!(context.getSource() instanceof Player))return 0;
                    BungeePlayer player = new BungeePlayer(context.getSource());
                    if (!player.api().isTeam()){
                        player.sendMessage(LanguagePath.NETWORK_COMMAND_NOSTAFF);
                        return 0;
                    }
                    if (player.api().cloud().cloudAPI().getConnectedService().getServerName().startsWith("Build-")){
                        player.sendMessage(LanguagePath.PROXY_STAFF_COMMAND_BUILDSERVER_ALREADY);
                        return 0;
                    }
                    player.api().cloud().cloudAPI().getPlayerExecutor().connectToGroup("Build", ServerSelectorType.LOWEST_PLAYERS);
                    player.sendMessage(LanguagePath.PROXY_STAFF_COMMAND_BUILDSERVER_SUCCESS);
                    return 1;
                }).build();

        BrigadierCommand command = new BrigadierCommand(node);
    }

}
