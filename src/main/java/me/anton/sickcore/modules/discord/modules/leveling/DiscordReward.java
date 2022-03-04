package me.anton.sickcore.modules.discord.modules.leveling;

import me.anton.sickcore.api.player.discordPlayer.DiscordPlayer;

public abstract class DiscordReward {

    public abstract void assign(DiscordPlayer player);

    public abstract String getName();
    public abstract String getType();
    public abstract String getValue();
    public abstract int expire();

}
