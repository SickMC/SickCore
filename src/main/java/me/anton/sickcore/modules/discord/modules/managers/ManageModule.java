package me.anton.sickcore.modules.discord.modules.managers;

import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.modules.IDiscordModule;

public class ManageModule implements IDiscordModule {
    @Override
    public void load() {
        register();
    }

    @Override
    public void unload() {

    }

    public void register(){
        //Discord
        DiscordModule module = DiscordModule.getInstance();
        module.getJda().addEventListener(new SuggestionManager());
    }

}
