package me.anton.sickcore.modules.discord.modules.leveling.rewards;

import lombok.Getter;
import me.anton.sickcore.api.player.discordPlayer.DiscordPlayer;
import me.anton.sickcore.modules.discord.modules.leveling.DiscordReward;

public class CoinReward extends DiscordReward {

    @Getter
    private int coins;

    public CoinReward(int coins){
        this.coins = coins;
    }

    @Override
    public void assign(DiscordPlayer player) {
        player.api().setCoins(player.api().getCoins() + coins);
    }

    @Override
    public String getName() {
        return "Coin Reward";
    }

    @Override
    public String getValue() {
        return String.valueOf(coins);
    }

    @Override
    public String getType() {
        return "Coins";
    }

    @Override
    public int expire() {
        return 0;
    }
}
