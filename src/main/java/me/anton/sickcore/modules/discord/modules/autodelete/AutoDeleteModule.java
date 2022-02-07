package me.anton.sickcore.modules.discord.modules.autodelete;

import lombok.Getter;
import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.modules.IDiscordModule;
import org.bson.Document;

@Getter
public class AutoDeleteModule implements IDiscordModule {

    private Document document = getModule().getDiscordModel().getDocument("type", "autoDelete");
    @Getter
    private static AutoDeleteModule instance;

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
        getModule().getJda().addEventListener(new DeleteListener());
    }

    @Override
    public DiscordModule getModule() {
        return IDiscordModule.super.getModule();
    }
}
