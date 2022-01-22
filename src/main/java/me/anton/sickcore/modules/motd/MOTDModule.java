package me.anton.sickcore.modules.motd;

import me.anton.sickcore.core.BungeeCore;
import me.anton.sickcore.core.module.IModule;

public class MOTDModule implements IModule {
    @Override
    public void load() {

        register();
    }

    @Override
    public void unload() {

    }

    @Override
    public void register() {
        BungeeCore.getInstance().getProvider().register(new MOTDHandler());
    }
}
