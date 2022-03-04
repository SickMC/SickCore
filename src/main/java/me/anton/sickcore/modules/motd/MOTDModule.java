package me.anton.sickcore.modules.motd;

import lombok.Getter;
import me.anton.sickcore.api.database.Finder;
import me.anton.sickcore.core.BungeeCore;
import me.anton.sickcore.core.Core;
import me.anton.sickcore.core.module.globalmodule.GlobalModule;
import me.anton.sickcore.core.module.proxiedModule.ProxiedIModule;
import org.bson.Document;

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
                BungeeCore.getInstance().getProvider().register(new MOTDHandler());
            }
            case BUKKIT -> {

            }
        }

    }

}
