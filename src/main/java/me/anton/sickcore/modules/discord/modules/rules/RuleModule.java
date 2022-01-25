package me.anton.sickcore.modules.discord.modules.rules;

import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.modules.IDiscordModule;

public class RuleModule implements IDiscordModule {

    @Override
    public void load() {
        register();
    }

    @Override
    public void unload() {

    }

    @Override
    public void register() {
        DiscordModule.getInstance().getJda().addEventListener(new RuleReaction());
        new RuleMessage();
    }
}
