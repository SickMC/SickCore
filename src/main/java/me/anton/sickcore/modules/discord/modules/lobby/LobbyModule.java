package me.anton.sickcore.modules.discord.modules.lobby;

import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.modules.IDiscordModule;

public class LobbyModule implements IDiscordModule {
    @Override
    public void load() {
        register();
    }

    @Override
    public void unload() {

    }

    @Override
    public void register() {
        DiscordModule.getInstance().getJda().addEventListener(new LobbyLog());
    }


}
