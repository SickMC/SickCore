package me.anton.sickcore.api.player.cloudPlayer;

import eu.thesimplecloud.api.player.ICloudPlayer;
import eu.thesimplecloud.api.player.text.CloudText;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;

public interface ICloudAPIPlayer {

    public void sendMessage(String en, String de);

    public void sendMessage(CloudText en, CloudText de);

    public IAPIPlayer api();

    public ICloudPlayer cloudAPI();

}
