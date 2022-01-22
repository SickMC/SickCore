package me.anton.sickcore.api.player.cloudPlayer;

import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.player.ICloudPlayer;
import eu.thesimplecloud.api.player.text.CloudText;
import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.apiPlayer.enums.Language;

import java.util.UUID;

public class CloudAPIPlayer implements ICloudAPIPlayer {


    private final IAPIPlayer player;
    private final ICloudPlayer cloudPlayer;

    public CloudAPIPlayer(IAPIPlayer player){
        this.player = player;
        this.cloudPlayer = CloudAPI.getInstance().getCloudPlayerManager().getCloudPlayer(player.getUUID()).getBlocking();
    }

    public CloudAPIPlayer(UUID uuid){
        this.player = new APIPlayer(uuid);
        this.cloudPlayer = CloudAPI.getInstance().getCloudPlayerManager().getCloudPlayer(uuid).getBlocking();
    }

    public CloudAPIPlayer(ICloudPlayer cloudPlayer){
        this.cloudPlayer = cloudPlayer;
        this.player = new APIPlayer(cloudPlayer.getUniqueId());
    }

    @Override
    public void sendMessage(String en, String de) {
        if (this.player.getLanguage().equals(Language.ENGLISCH))
            cloudPlayer.sendMessage(en);
        else if (this.player.getLanguage().equals(Language.DEUTSCH))
            cloudPlayer.sendMessage(de);
    }

    @Override
    public void sendMessage(CloudText en, CloudText de) {
        if (this.player.getLanguage().equals(Language.ENGLISCH))
            cloudPlayer.sendMessage(en);
        else if (this.player.getLanguage().equals(Language.DEUTSCH))
            cloudPlayer.sendMessage(de);
    }

    @Override
    public IAPIPlayer api() {
        return player;
    }

    @Override
    public ICloudPlayer cloudAPI() {
        return cloudPlayer;
    }
}
