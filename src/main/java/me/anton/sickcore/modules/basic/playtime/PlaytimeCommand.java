package me.anton.sickcore.modules.basic.playtime;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.player.IOfflineCloudPlayer;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bungeePlayer.BungeePlayer;
import me.anton.sickcore.api.utils.common.Replacable;
import me.anton.sickcore.api.utils.common.TimeUtils;
import me.anton.sickcore.api.utils.minecraft.messages.ConsoleMessages;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@CommandAlias("playtime|pt")
public class PlaytimeCommand extends BaseCommand {

    @Default
    @Syntax("<Player>")
    @Description("Shows the playtime of the player")
    public void onCMDD(CommandSender sender, @Optional String targetRaw){
        if (!(sender instanceof ProxiedPlayer)) {
            ConsoleMessages.noPlayerBungee(sender);
            return;
        }

        BungeePlayer player = new BungeePlayer(sender);

        if (targetRaw != null) {
            IOfflineCloudPlayer cloudPlayer = CloudAPI.getInstance().getCloudPlayerManager().getOfflineCloudPlayer(targetRaw).getBlockingOrNull();
            if (cloudPlayer == null){
                player.getPlayer().sendMessage(new TextComponent("§4This player cannot be found!"));
                return;
            }

            player.sendMessage(player.api().languageString(LanguagePath.PROXY_COMMAND_PLAYTIME_TIME).replace(new Replacable("%playtime%", TimeUtils.formatMillis(cloudPlayer.getOnlineTime()))));
            return;
        }

        player.sendMessage(player.api().languageString(LanguagePath.PROXY_COMMAND_PLAYTIME_TIME).replace(new Replacable("%playtime%", TimeUtils.formatMillis(player.api().cloud().cloudAPI().getOnlineTime()))));
    }


}
