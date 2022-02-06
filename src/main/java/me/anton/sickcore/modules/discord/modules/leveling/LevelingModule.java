package me.anton.sickcore.modules.discord.modules.leveling;

import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.modules.IDiscordModule;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class LevelingModule implements IDiscordModule {

    @Override
    public void load() {
        register();
    }

    @Override
    public void unload() {

    }

    @Override
    public void register() {
        DiscordModule.getInstance().getJda().addEventListener(new LevelingListener());
    }
}
