package me.anton.sickcore.core.module.proxiedModule;

import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.motd.MOTDModule;
import me.anton.sickcore.modules.msg.MsgModule;

import java.util.Arrays;
import java.util.List;

public class ProxiedModuleHandler {

    private List<ProxiedIModule> modules = Arrays.asList(new MsgModule());

    public void loadModules(){
        modules.forEach(ProxiedIModule::load);
    }

    public void unLoadModules(){
        modules.forEach(ProxiedIModule::unload);
    }

}
