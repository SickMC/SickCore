package me.anton.sickcore.modules.discord.modules.leveling.rewards;

import lombok.Getter;
import me.anton.sickcore.api.player.discordPlayer.DiscordPlayer;
import me.anton.sickcore.modules.discord.modules.leveling.DiscordReward;
import me.anton.sickcore.modules.rank.Rank;

import java.util.concurrent.TimeUnit;

public class RankReward extends DiscordReward {

    @Getter
    private Rank rank;
    private int expire = 0;
    public RankReward(Rank rank){
        this.rank = rank;
        this.expire = -1;
    }

    public RankReward(Rank rank, int daysExpire){
        this.rank = rank;
        this.expire = daysExpire;
    }

    @Override
    public void assign(DiscordPlayer player) {
        if (expire == -1)player.api().setRank(rank);
        else player.api().setRank(rank, TimeUnit.DAYS, expire);
    }

    @Override
    public String getName() {
        return "Rank Reward";
    }

    @Override
    public String getValue() {
        return rank.getName();
    }

    @Override
    public String getType() {
        return "Rank";
    }

    @Override
    public int expire() {
        return expire;
    }
}
