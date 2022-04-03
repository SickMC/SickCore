package me.anton.sickcore.modules.staff;

import lombok.Getter;
import me.anton.sickcore.oldcore.BukkitCore;
import me.anton.sickcore.oldcore.module.globalmodule.GlobalModule;

public class StaffModule extends GlobalModule {

    @Getter
    private static StaffModule instance;

    @Override
    public void load() {
        instance = this;
        initializeConfig("staff");
        switch (getEnvironment()){
            case BUNGEECORD -> {

            }
            case BUKKIT -> {
                BukkitCore.getInstance().getManager().registerCommand(new StaffCommand());
            }
        }
    }

    @Override
    public void unload() {
        instance = null;
    }
}
