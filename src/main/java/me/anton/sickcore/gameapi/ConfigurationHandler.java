package me.anton.sickcore.gameapi;

import me.anton.sickcore.oldcore.UtilConfiguration;
import me.anton.sickcore.games.monopoly.MonopolyGame;

public class ConfigurationHandler {

    public static GameConfiguration getMonopolyConfig(){
        return new GameConfiguration(new MonopolyGame(), "monopoly");
    }

    public static UtilConfiguration getMaintenanceConfig(){
        return new UtilConfiguration("maintenance");
    }

}
