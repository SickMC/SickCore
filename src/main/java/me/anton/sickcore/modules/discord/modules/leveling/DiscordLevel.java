package me.anton.sickcore.modules.discord.modules.leveling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.anton.sickcore.api.player.apiPlayer.enums.Rank;
import me.anton.sickcore.modules.discord.modules.leveling.rewards.CoinReward;
import me.anton.sickcore.modules.discord.modules.leveling.rewards.RankReward;

@AllArgsConstructor
@Getter
public enum DiscordLevel {

    WOOD(1, 150, "Wood", new CoinReward(1000)),
    STONE(151, 500, "Stone", new RankReward(Rank.VIP, 3)),
    IRON(501, 1000, "Iron", new CoinReward(5000)),
    GOLD(1001, 3000, "Gold", new RankReward(Rank.MVP, 7)),
    DIAMOND(3001, 5000, "Diamond", new CoinReward(10000)),
    NETHERITE(5001, 10000, "Netherite", new RankReward(Rank.MVP));

    public int start;
    public int end;
    public String name;
    public DiscordReward reward;

    public DiscordLevel getNext(){
        boolean current = false;
        for (DiscordLevel value : values()) {
            if(current) return value;
            if(value == this) current = true;
        }
        return this;
    }

}
