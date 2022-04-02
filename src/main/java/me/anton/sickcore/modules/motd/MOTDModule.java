package me.anton.sickcore.modules.motd;

import lombok.Getter;
import me.anton.sickcore.core.ProxyCore;
import me.anton.sickcore.core.module.globalmodule.GlobalModule;

@Getter
public class MOTDModule extends GlobalModule {

    @Getter
    private static MOTDModule instance;

    @Override
    public void load() {
        instance = this;
        initializeConfig("motd");

        register();
    }

    @Override
    public void unload() {

    }

    public void register() {
        switch (getEnvironment()){
            case BUNGEECORD -> {
                ProxyCore.getInstance().getProvider().register(new MOTDHandler());
            }
            case BUKKIT -> {

            }
        }

    }

}
