package me.anton.sickcore.modules.basic;

import lombok.Getter;
import me.anton.sickcore.oldcore.BukkitCore;
import me.anton.sickcore.oldcore.ProxyCore;
import me.anton.sickcore.oldcore.Core;
import me.anton.sickcore.oldcore.module.globalmodule.GlobalModule;
import me.anton.sickcore.modules.basic.buildserver.BuildServerCommand;
import me.anton.sickcore.modules.basic.commandremake.FindCommand;
import me.anton.sickcore.modules.basic.commandremake.ListCommand;
import me.anton.sickcore.modules.basic.commandremake.SendCommand;
import me.anton.sickcore.modules.basic.commandremake.ServerCommand;
import me.anton.sickcore.modules.basic.lobby.LobbyCommand;
import me.anton.sickcore.modules.basic.lobby.LobbyCommandAd;
import me.anton.sickcore.modules.basic.playtime.PlaytimeCommand;

@Getter
public class BasicModule extends GlobalModule {

    private ProxyCore bungeeCore;
    private BukkitCore bukkitCore;

    @Override
    public void load() {
        switch (Core.getInstance().getEnvironment()){
            case BUKKIT -> {
                this.bukkitCore = BukkitCore.getInstance();

                bukkitCore.getManager().registerCommand(new ServerCommand(), true);
                bukkitCore.getManager().registerCommand(new SendCommand(), true);
                bukkitCore.getManager().registerCommand(new FindCommand(), true);
                bukkitCore.getManager().registerCommand(new ListCommand(), true);
            }
            case BUNGEECORD -> {
                this.bungeeCore = ProxyCore.getInstance();
                bungeeCore.getManager().registerCommand(new LobbyCommand());
                bungeeCore.getProvider().register(new LobbyCommandAd());

                bungeeCore.getManager().registerCommand(new BuildServerCommand());

                bungeeCore.getManager().registerCommand(new PlaytimeCommand());
            }
        }
    }

    @Override
    public void unload() {
        this.bukkitCore = null;
        this.bungeeCore = null;
    }

}
