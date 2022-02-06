package me.anton.sickcore.games.survival;

import eu.thesimplecloud.api.eventapi.IListener;
import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.core.BukkitCore;
import me.anton.sickcore.core.game.IGame;
import me.anton.sickcore.games.defaults.all.appereance.TablistBuilder;
import me.anton.sickcore.games.defaults.additional.commands.EnderchestSee;
import me.anton.sickcore.games.defaults.all.InvSee;
import me.anton.sickcore.games.defaults.additional.commands.SharePos;
import me.anton.sickcore.games.defaults.additional.heads.MobDrops;
import me.anton.sickcore.games.defaults.additional.heads.SkullDrops;
import me.anton.sickcore.games.defaults.additional.survival.StartingEquip;
import me.anton.sickcore.games.survival.appereance.SurvivalChat;
import me.anton.sickcore.games.survival.spawn.Elytra;
import me.anton.sickcore.games.survival.spawn.Protection;
import me.anton.sickcore.games.survival.spawn.Teleport;

public class SurvivalGame extends IGame {

    TablistBuilder builder;

    @Override
    public void load() {
        registerListener();

        registerListener(new SurvivalChat(), new MobDrops(), new SkullDrops(), new StartingEquip(), new Teleport(), new Protection(), new Elytra());
        getCore().getManager().registerCommand(new EnderchestSee());
        getCore().getManager().registerCommand(new SharePos());
        builder = new TablistBuilder("Survival", true);
    }

    @Override
    public void unload() {

    }

    @Override
    public void registerListener(BukkitHandler... handler) {
        super.registerListener(handler);
    }

    @Override
    public void registerCloudListener(IListener listener) {
        super.registerCloudListener(listener);
    }

    @Override
    public BukkitCore getCore() {
        return super.getCore();
    }

    @Override
    public String getName() {
        return "Survival";
    }

    @Override
    public DatabaseModel getModel() {
        return super.getModel();
    }
}
