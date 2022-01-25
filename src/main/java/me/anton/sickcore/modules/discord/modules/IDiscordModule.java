package me.anton.sickcore.modules.discord.modules;

import me.anton.sickcore.modules.discord.DiscordModule;

public interface IDiscordModule {

    public void load();
    public void unload();
    public void register();
    public default DiscordModule getModule(){
        return DiscordModule.getInstance();
    }

}
