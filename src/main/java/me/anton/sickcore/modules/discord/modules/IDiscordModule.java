package me.anton.sickcore.modules.discord.modules;

import me.anton.sickcore.modules.discord.DiscordModule;

public interface IDiscordModule {

    public default void load(){
        register();
    };
    public void unload();
    public void register();
    public default DiscordModule getModule(){
        return DiscordModule.getInstance();
    }

}
