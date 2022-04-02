package me.anton.sickcore.modules.discord.modules.leveling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.anton.sickcore.modules.discord.modules.leveling.rewards.RankReward;
import me.anton.sickcore.modules.rank.Rank;

@AllArgsConstructor
@Getter
public enum DiscordLevel {

    WOOD(1, 150, "Wood", null),
    STONE(151, 500, "Stone", new RankReward(new Rank("VIP"), 3)),
    IRON(501, 1000, "Iron", null),
    GOLD(1001, 3000, "Gold", new RankReward(new Rank("MVP"), 7)),
    DIAMOND(3001, 5000, "Diamond", null),
    NETHERITE(5001, 10000, "Netherite", new RankReward(new Rank("MVP")));

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
