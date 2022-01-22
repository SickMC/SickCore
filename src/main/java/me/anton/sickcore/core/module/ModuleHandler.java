package me.anton.sickcore.core.module;

import me.anton.sickcore.modules.basic.BasicModule;
import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.motd.MOTDModule;

import java.util.Arrays;
import java.util.List;

public class ModuleHandler {

    private List<IModule> modules = Arrays.asList(new DiscordModule(), new MOTDModule(), new BasicModule());

    public void loadModules(){
        modules.forEach(IModule::load);
    }

    public void unLoadModules(){
        modules.forEach(IModule::unload);
    }

}
