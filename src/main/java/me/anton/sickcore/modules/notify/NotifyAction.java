package me.anton.sickcore.modules.notify;

import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.player.ICloudPlayer;
import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;

public class NotifyAction {

    public NotifyAction(String message, NotifyType type) {
        for (ICloudPlayer player : CloudAPI.getInstance().getCloudPlayerManager().getAllCachedObjects()){
            IAPIPlayer iapiPlayer = new APIPlayer(player.getUniqueId());
            if (!iapiPlayer.isTeam())continue;
            if (!iapiPlayer.getNotifyConfig().getBoolean(type.name().toLowerCase()))continue;
            player.sendMessage(message);
        }
    }

}
