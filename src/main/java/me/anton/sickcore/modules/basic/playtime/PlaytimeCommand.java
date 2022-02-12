package me.anton.sickcore.modules.basic.playtime;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bungeePlayer.BungeePlayer;
import me.anton.sickcore.api.player.bungeePlayer.IBungeePlayer;
import me.anton.sickcore.api.utils.common.Replacable;
import me.anton.sickcore.api.utils.common.TimeUtils;
import me.anton.sickcore.api.utils.minecraft.messages.ConsoleMessages;
import me.anton.sickcore.api.utils.minecraft.player.uniqueid.UUIDFetcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@CommandAlias("playtime|onlinetime|ot|pt")
public class PlaytimeCommand extends BaseCommand {

    @CommandCompletion("@Players")
    @Syntax("<player>")
    @Description("Shows the onlinetime of the player")
    public void onCMd(CommandSender sender, String target){
        if (!(sender instanceof ProxiedPlayer)) {
            ConsoleMessages.noPlayerBungee(sender);
            return;
        }

        IBungeePlayer player = new BungeePlayer(sender);
        IBungeePlayer targetpl = new BungeePlayer(UUIDFetcher.fetchUniqueId(target));

        player.sendMessage(targetpl.api().languageString(LanguagePath.PROXY_COMMAND_PLAYTIME_TIME).replace(new Replacable("%playtime%", TimeUtils.formatMillis(targetpl.api().cloud().cloudAPI().getOnlineTime()))));
    }

    @Description("Shows the onlinetime of the player")
    public void onCMDD(CommandSender sender){
        if (!(sender instanceof ProxiedPlayer)) {
            ConsoleMessages.noPlayerBungee(sender);
            return;
        }

        IBungeePlayer player = new BungeePlayer(sender);

        player.sendMessage(player.api().languageString(LanguagePath.PROXY_COMMAND_PLAYTIME_TIME).replace(new Replacable("%playtime%", TimeUtils.formatMillis(player.api().cloud().cloudAPI().getOnlineTime()))));
    }

}
