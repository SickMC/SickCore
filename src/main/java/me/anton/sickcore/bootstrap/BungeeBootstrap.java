package me.anton.sickcore.bootstrap;

import me.anton.sickcore.core.BungeeCore;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeBootstrap extends Plugin {

    private BungeeCore core;

    @Override
    public void onEnable() {
        core = new BungeeCore(this);
        core.onLoad();
    }

    @Override
    public void onDisable() {
        core.onUnLoad();
    }
}
