package me.anton.sickcore.modules.service;

import com.velocitypowered.api.proxy.Player;
import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.ext.bridge.player.CloudPlayer;
import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.language.LanguageObject;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bungeePlayer.BungeePlayer;
import me.anton.sickcore.api.utils.common.Replacable;
import me.anton.sickcore.oldcore.Core;
import me.anton.sickcore.oldcore.Environment;
import me.anton.sickcore.oldcore.ProxyCore;
import net.kyori.adventure.text.Component;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class AutoRestart{

    public void run() {
        if (!ProxyCore.getInstance().isMainProxy())return;
        ProxyCore.getInstance().getPlugin().getScheduler().buildTask(ProxyCore.getInstance().getPlugin(), () -> {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            Date date = new Date(System.currentTimeMillis());
            String time = timeFormat.format(date);
            int remain = 60-(Integer.parseInt(time.split(":")[1]));

            if (!Core.getInstance().getEnvironment().equals(Environment.BUNGEECORD))return;

            switch (time) {
                case "00:30", "00:45", "00:50", "00:55", "00:56", "00:57", "00:58", "00:59" -> {
                    for (Player player : Core.getInstance().bungee().getPlugin().getAllPlayers()) {
                        APIPlayer iapiPlayer = new APIPlayer(player.getUniqueId());
                        BungeePlayer bungeePlayer = new BungeePlayer(player);
                        bungeePlayer.sendMessage(new LanguageObject(iapiPlayer, LanguagePath.PROXY_SERVICE_RESTART_WARNING).replace(new Replacable("%time%", String.valueOf(remain)), new Replacable("%s%", "s"), new Replacable("%n%", "n")));
                    }
                    break;
                }
                case "01:00" -> {
                    for (Player player : Core.getInstance().bungee().getPlugin().getAllPlayers()) {
                        APIPlayer iapiPlayer = new APIPlayer(player.getUniqueId());
                        player.disconnect(Component.text(iapiPlayer.languageString(LanguagePath.PROXY_SERVICE_RESTART_NOW).build()));
                    }
                    CloudNetDriver.getInstance().getCloudServiceProvider().getStartedCloudServices().forEach(service -> {
                        if (service.getName().startsWith("Proxy-")) return;
                        service.provider().stop();
                    });
                    ProxyCore.getInstance().getPlugin().shutdown();
                }
            }
    }).repeat(Duration.ofMinutes(1)).schedule();
    }

}
