package me.anton.sickcore.modules.rank;

import lombok.Getter;
import me.anton.sickcore.core.module.globalmodule.GlobalModule;
import me.anton.sickcore.modules.rank.permission.BukkitPermissionListener;
import me.anton.sickcore.modules.rank.permission.PermissionCheck;

public class RankModule extends GlobalModule {

    @Getter
    private static RankModule instance;

    @Override
    public void load() {
        instance = this;

        switch (getEnvironment()){
            case BUKKIT -> {
                registerBukkitEvents(new BukkitPermissionListener());
            }
            case BUNGEECORD -> {
                registerVelocityEvents(new PermissionCheck(), new RankListener());
            }
        }
    }

    @Override
    public void unload() {
        instance = null;
    }
}
