package me.anton.sickcore.games.survival;

import lombok.Getter;
import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.games.survival.heads.HeadCollection;
import me.anton.sickcore.games.survival.heads.HeadManageCommand;
import me.anton.sickcore.games.survival.heads.MobDrops;
import me.anton.sickcore.games.survival.heads.SkullDrops;
import me.anton.sickcore.games.survival.survival.StartingEquip;
import me.anton.sickcore.games.survival.appereance.SurvivalChat;
import me.anton.sickcore.games.survival.spawn.Elytra;
import me.anton.sickcore.games.survival.spawn.Protection;
import me.anton.sickcore.games.survival.spawn.Teleport;
import me.anton.sickcore.gameapi.AbstractGame;

public class SurvivalGame extends AbstractGame {

    @Getter
    private static SurvivalGame instance;

    @Override
    public void onLoad() {
        instance = this;
        registerListeners(new SurvivalChat(), new MobDrops(), new SkullDrops(), new StartingEquip(), new MobDrops(), new SkullDrops());
        registerCommands(new EnderchestSee(), new SharePos(), new HeadCollection(), new HeadManageCommand());
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

    @Override
    public DatabaseModel getPlayerModel() {
        return super.getPlayerModel();
    }

    @Override
    public boolean isStaff() {
        return false;
    }
}
