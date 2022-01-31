package me.anton.sickcore.modules.discord.modules.staffcommands;

import lombok.Getter;
import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.modules.IDiscordModule;

public class StaffCommandModule implements IDiscordModule {

    @Getter
    private static StaffCommandModule instance;

    @Override
    public void load() {
        instance = this;

        register();
    }


    @Override
    public void unload() {

    }

    @Override
    public void register() {

    }

    @Override
    public DiscordModule getModule() {
        return IDiscordModule.super.getModule();
    }
}
