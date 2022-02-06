package me.anton.sickcore.modules.discord.modules;

import me.anton.sickcore.modules.discord.DiscordModule;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public interface IDiscordModule {

    public void load();
    public void unload();
    public void register();
    public default DiscordModule getModule(){
        return DiscordModule.getInstance();
    }

}
