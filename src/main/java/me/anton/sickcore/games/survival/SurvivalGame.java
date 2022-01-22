package me.anton.sickcore.games.survival;

import eu.thesimplecloud.api.eventapi.IListener;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.core.BukkitCore;
import me.anton.sickcore.core.game.IGame;
import me.anton.sickcore.games.defaults.commands.EnderchestSee;
import me.anton.sickcore.games.defaults.commands.InvSee;
import me.anton.sickcore.games.defaults.commands.SharePos;
import me.anton.sickcore.games.defaults.heads.MobDrops;
import me.anton.sickcore.games.defaults.heads.SkullDrops;
import me.anton.sickcore.games.defaults.survival.StartingEquip;
import me.anton.sickcore.games.survival.appereance.SurvivalChat;
import me.anton.sickcore.games.survival.appereance.SurvivalTablist;
import me.anton.sickcore.games.survival.appereance.SurvivalTablistCloudProvider;
import me.anton.sickcore.games.survival.spawn.Elytra;
import me.anton.sickcore.games.survival.spawn.Protection;
import me.anton.sickcore.games.survival.spawn.Teleport;

public class SurvivalGame implements IGame {
    @Override
    public void load() {
        registerListener();

    }

    @Override
    public void unload() {

    }

    @Override
    public void registerListener() {
        registerListener(new SurvivalChat(), new SurvivalTablist(), new MobDrops(), new SkullDrops(), new StartingEquip(), new Teleport(), new Protection(), new Elytra());

        registerCloudListener(new SurvivalTablistCloudProvider());
        getCore().getManager().registerCommand(new EnderchestSee());
        getCore().getManager().registerCommand(new InvSee());
        getCore().getManager().registerCommand(new SharePos());
    }


    @Override
    public void registerListener(BukkitHandler... handler) {
        IGame.super.registerListener(handler);
    }

    @Override
    public void registerCloudListener(IListener listener) {
        IGame.super.registerCloudListener(listener);
    }

    @Override
    public BukkitCore getCore() {
        return IGame.super.getCore();
    }

    @Override
    public String getName() {
        return "Survival";
    }
}
