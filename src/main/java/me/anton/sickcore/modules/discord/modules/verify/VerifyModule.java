package me.anton.sickcore.modules.discord.modules.verify;

import lombok.Getter;
import me.anton.sickcore.api.player.bungeePlayer.BungeePlayer;
import me.anton.sickcore.oldcore.ProxyCore;
import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.modules.IDiscordModule;

import java.util.HashMap;

@Getter
public class VerifyModule implements IDiscordModule {

    @Getter
    private static VerifyModule verifyModule;
    private HashMap<BungeePlayer, Integer> verifyList = new HashMap<>();
    private HashMap<Integer, BungeePlayer> verifyListReturn = new HashMap<>();

    @Override
    public void load() {
        verifyModule = this;

        register();
    }

    @Override
    public void unload() {

    }

    @Override
    public void register() {
        ProxyCore.getInstance().getManager().registerCommand(new MCDiscordCommand());
        DiscordModule.getInstance().getJda().addEventListener(new VerifyCleaner());
    }
}
