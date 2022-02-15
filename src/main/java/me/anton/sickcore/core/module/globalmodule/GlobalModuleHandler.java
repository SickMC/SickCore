package me.anton.sickcore.core.module.globalmodule;

import lombok.Getter;
import me.anton.sickcore.modules.basic.BasicModule;
import me.anton.sickcore.modules.notify.NotifyModule;

import java.util.Arrays;
import java.util.List;

public class GlobalModuleHandler {

    @Getter
    public List<GlobalModule> modules = Arrays.asList(new NotifyModule(), new BasicModule());

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
