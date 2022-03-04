package me.anton.sickcore.core.module.globalmodule;

import me.anton.sickcore.api.utils.common.Logger;
import me.anton.sickcore.core.Core;
import me.anton.sickcore.core.Environment;

public abstract class GlobalModule {

    private String name = null;

    public abstract void load();

    public abstract void unload();

    public Environment getEnvironment(){
        return Core.getInstance().getEnvironment();
    }

    public void initializeConfig(String name){
        this.name = name;
    }

    public ModuleConfiguration getConfig(){
        if (name == null) {
            Logger.error("You have to set a name for this config", this.getClass());
            return null;
        }
        return new ModuleConfiguration(this, name);
    }

}
