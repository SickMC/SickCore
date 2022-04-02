package me.anton.sickcore.modules.discord.modules.ranks;

import me.anton.sickcore.core.Core;
import me.anton.sickcore.modules.discord.modules.IDiscordModule;
import me.anton.sickcore.modules.discord.modules.ranks.listeners.RankListener;

public class RankModule implements IDiscordModule {

    @Override
    public void load() {
        register();
    }

    @Override
    public void unload() {

    }

    public void register(){
        //Bungee
        Core.getInstance().bungee().getProvider().register(new RankListener());
    }
}
