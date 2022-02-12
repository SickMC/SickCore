package me.anton.sickcore.game.games.survival;

import me.anton.sickcore.game.defaults.additional.commands.EnderchestSee;
import me.anton.sickcore.game.defaults.additional.commands.SharePos;
import me.anton.sickcore.game.defaults.additional.heads.MobDrops;
import me.anton.sickcore.game.defaults.additional.heads.SkullDrops;
import me.anton.sickcore.game.defaults.additional.survival.StartingEquip;
import me.anton.sickcore.game.games.survival.appereance.SurvivalChat;
import me.anton.sickcore.game.games.survival.spawn.Elytra;
import me.anton.sickcore.game.games.survival.spawn.Protection;
import me.anton.sickcore.game.games.survival.spawn.Teleport;
import me.anton.sickcore.gameapi.AbstractGame;

public class SurvivalGame extends AbstractGame {

    @Override
    public void onLoad() {
        registerListener(new SurvivalChat(), new MobDrops(), new SkullDrops(), new StartingEquip(), new Teleport(), new Protection(), new Elytra(), new MobDrops(), new SkullDrops());
        getCore().getManager().registerCommand(new EnderchestSee());
        getCore().getManager().registerCommand(new SharePos());
    }

    @Override
    public void onUnload() {

    }

    @Override
    public boolean nick() {
        return true;
    }

    @Override
    public String getName() {
        return "Survival";
    }
}
