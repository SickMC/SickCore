package me.anton.sickcore.modules.punishment;

import lombok.Getter;
import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.core.Core;
import me.anton.sickcore.core.module.globalmodule.GlobalModule;

@Getter
public class PunishmentModule extends GlobalModule {

    @Getter
    private static PunishmentModule instance;
    private DatabaseModel punishments;


    @Override
    public void load() {
        instance = this;

        this.punishments = Core.getInstance().getPunishmentModel();

        switch (Core.getInstance().getEnvironment()){
            case BUKKIT -> {

            }
            case BUNGEECORD -> {

            }
        }

    }

    @Override
    public void unload() {
        switch (Core.getInstance().getEnvironment()){
            case BUKKIT -> {

            }
            case BUNGEECORD -> {

            }
        }
    }
}
