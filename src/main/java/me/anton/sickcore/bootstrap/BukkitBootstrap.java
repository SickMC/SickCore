package me.anton.sickcore.bootstrap;

import me.anton.sickcore.core.BukkitCore;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitBootstrap extends JavaPlugin {

    private BukkitCore bukkitCore;

    @Override
    public void onEnable() {
        bukkitCore = new BukkitCore(this);
        bukkitCore.onLoad();
    }

    @Override
    public void onDisable() {
        bukkitCore.onUnLoad();
    }
}
