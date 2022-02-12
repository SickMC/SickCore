package me.anton.sickcore.modules.msg;

import me.anton.sickcore.core.BungeeCore;
import me.anton.sickcore.core.module.proxiedModule.ProxiedIModule;

public class MsgModule implements ProxiedIModule {

    @Override
    public void load() {
        BungeeCore.getInstance().getManager().registerCommand(new MsgCommand());

        register();
    }

    @Override
    public void unload() {

    }

    @Override
    public void register() {

    }
}
