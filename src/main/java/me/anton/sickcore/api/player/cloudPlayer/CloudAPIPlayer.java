package me.anton.sickcore.api.player.cloudPlayer;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.ext.bridge.player.CloudPlayer;
import de.dytanic.cloudnet.ext.bridge.player.ICloudPlayer;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.apiPlayer.language.Language;

import java.util.UUID;

public class CloudAPIPlayer implements IAPIPlayer {


    private final APIPlayer player;
    private final ICloudPlayer cloudPlayer;

    public CloudAPIPlayer(APIPlayer player){
        this.player = player;
        this.cloudPlayer = CloudNetDriver.getInstance().getServicesRegistry().getFirstService(IPlayerManager.class).getOnlinePlayer(player.getUUID());
    }

    public CloudAPIPlayer(UUID uuid){
        this.player = new APIPlayer(uuid);
        this.cloudPlayer = CloudNetDriver.getInstance().getServicesRegistry().getFirstService(IPlayerManager.class).getOnlinePlayer(uuid);
    }

    public CloudAPIPlayer(ICloudPlayer cloudPlayer){
        this.cloudPlayer = cloudPlayer;
        this.player = new APIPlayer(cloudPlayer.getUniqueId());
    }

    
    public void sendMessage(String en, String de) {
        if (this.player.getLanguage().equals(Language.ENGLISCHUK))
            cloudPlayer.getPlayerExecutor().sendChatMessage(en);
        else if (this.player.getLanguage().equals(Language.DEUTSCHDE))
            cloudPlayer.getPlayerExecutor().sendChatMessage(de);
    }
    
    public APIPlayer api() {
        return player;
    }

    public ICloudPlayer cloudAPI() {
        return cloudPlayer;
    }

    
    public UUID getUniqueID() {
        return null;
    }
}
