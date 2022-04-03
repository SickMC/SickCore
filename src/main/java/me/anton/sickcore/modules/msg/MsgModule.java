package me.anton.sickcore.modules.msg;

import me.anton.sickcore.oldcore.ProxyCore;
import me.anton.sickcore.oldcore.module.proxiedModule.ProxiedIModule;

public class MsgModule implements ProxiedIModule {

    @Override
    public void load() {
        ProxyCore.getInstance().getManager().registerCommand(new MsgCommand());

        register();
    }

    @Override
    public void unload() {

    }

    @Override
    public void register() {

    }
}
