package me.anton.sickcore.games.build;

import me.anton.sickcore.oldcore.BukkitCore;
import me.anton.sickcore.games.build.appereance.BuildChat;
import me.anton.sickcore.games.build.game.PositionCommand;
import me.anton.sickcore.games.build.tools.BuildTools;
import me.anton.sickcore.games.build.tools.invisitemframes.InvisFrameUpdate;
import me.anton.sickcore.gameapi.AbstractGame;

public class BuildGame extends AbstractGame {

    @Override
    public void onLoad() {
        registerListeners(new BuildChat(), new InvisFrameUpdate());

        BukkitCore.getInstance().getManager().registerCommand(new BuildTools(), true);
        getCore().getManager().registerCommand(new PositionCommand(), true);
    }

    @Override
    public void onUnload() {

    }

    @Override
    public boolean nick() {
        return false;
    }

    @Override
    public String getName() {
        return "Build";
    }

    @Override
    public boolean isStaff() {
        return true;
    }


}
