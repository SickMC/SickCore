package me.anton.sickcore.modules.discord.modules.leveling.rewards;

import eu.thesimplecloud.module.permission.player.PlayerPermissionGroupInfo;
import lombok.Getter;
import me.anton.sickcore.api.player.apiPlayer.enums.Rank;
import me.anton.sickcore.api.player.discordPlayer.IDiscordPlayer;
import me.anton.sickcore.modules.discord.modules.leveling.DiscordReward;

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
    public void assign(IDiscordPlayer player) {
        if (expire == -1)player.api().perm().addPermissionGroup(new PlayerPermissionGroupInfo(rank.getName(), -1));
        else {
            long timeout = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(expire);
            player.api().perm().addPermissionGroup(new PlayerPermissionGroupInfo(rank.getName(), timeout));
        }
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
