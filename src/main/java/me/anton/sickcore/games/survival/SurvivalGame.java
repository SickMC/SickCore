package me.anton.sickcore.games.survival;

import eu.thesimplecloud.api.eventapi.IListener;
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

public class SurvivalGame implements IGame {
    @Override
    public void load() {
        register();

    }

    @Override
    public void unload() {

    }

    @Override
    public void register() {
        getProvider().register(new SurvivalChat());
        getProvider().register(new SurvivalTablist());
        getProvider().register(new MobDrops());
        getProvider().register(new SkullDrops());
        getProvider().register(new StartingEquip());

        registerCloudListener(new SurvivalTablistCloudProvider());
        getCore().getManager().registerCommand(new EnderchestSee());
        getCore().getManager().registerCommand(new InvSee());
        getCore().getManager().registerCommand(new SharePos());
    }

    @Override
    public BukkitListenerProvider getProvider() {
        return IGame.super.getProvider();
    }

    @Override
    public void registerCloudListener(IListener listener) {
        IGame.super.registerCloudListener(listener);
    }

    @Override
    public BukkitCore getCore() {
        return IGame.super.getCore();
    }
}
