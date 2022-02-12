package me.anton.sickcore.modules.service;

import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.player.ICloudPlayer;
import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.apiPlayer.language.LanguageObject;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bungeePlayer.BungeePlayer;
import me.anton.sickcore.api.player.bungeePlayer.IBungeePlayer;
import me.anton.sickcore.api.utils.common.Replacable;
import me.anton.sickcore.core.Core;
import me.anton.sickcore.core.Environment;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AutoRestart implements Runnable{
    @Override
    public void run() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date(System.currentTimeMillis());
        String time = timeFormat.format(date);
        int remain = 60-(Integer.parseInt(time.split(":")[1]));

        if (!Core.getInstance().getEnvironment().equals(Environment.BUNGEECORD))return;

        switch (time) {
            case "00:30", "00:45", "00:50", "00:55", "00:56", "00:57", "00:58", "00:59" -> {
                for (ProxiedPlayer player : Core.getInstance().bungee().getPlugin().getProxy().getPlayers()) {
                    IAPIPlayer iapiPlayer = new APIPlayer(player.getUniqueId());
                    IBungeePlayer bungeePlayer = new BungeePlayer(player);
                    bungeePlayer.sendMessage(new LanguageObject(iapiPlayer, LanguagePath.PROXY_SERVICE_RESTART_WARNING).replace(new Replacable("%time%", String.valueOf(remain)), new Replacable("%s%", "s"), new Replacable("%n%", "n")));
                }
                break;
            }
            case "01:00" -> {
                for (ProxiedPlayer player : Core.getInstance().bungee().getPlugin().getProxy().getPlayers()) {
                    IAPIPlayer iapiPlayer = new APIPlayer(player.getUniqueId());
                    ICloudPlayer cloudPlayer = iapiPlayer.cloud().cloudAPI();
                    cloudPlayer.kick(iapiPlayer.languageString(LanguagePath.PROXY_SERVICE_RESTART_NOW).build());
                }
                CloudAPI.getInstance().getCloudServiceManager().getAllCachedObjects().forEach(service -> {
                    if (service.isProxy()) return;
                    service.shutdown().getBlocking();
                });
                CloudAPI.getInstance().getCloudServiceManager().getCloudServiceByName(CloudAPI.getInstance().getThisSidesName()).shutdown();
            }
        }
    }
}
