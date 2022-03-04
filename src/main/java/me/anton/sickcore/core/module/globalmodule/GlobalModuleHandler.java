package me.anton.sickcore.core.module.globalmodule;

import lombok.Getter;
import me.anton.sickcore.modules.basic.BasicModule;
import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.motd.MOTDModule;
import me.anton.sickcore.modules.notify.NotifyModule;
import me.anton.sickcore.modules.punishment.PunishmentModule;

import java.util.Arrays;
import java.util.List;

public class GlobalModuleHandler {

    @Getter
    public List<GlobalModule> modules = Arrays.asList(new NotifyModule(), new BasicModule(), new DiscordModule(), new MOTDModule());

    public void load(){
        for (GlobalModule module : modules) {
            module.load();
        }
    }

    public void unload(){
        for (GlobalModule module : modules) {
            module.unload();
        }
    }

}
