package me.anton.sickcore.api.handler.listeners.cloud;

import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.eventapi.IListener;

public class CloudListenerProvider {

    public static void register(IListener listener){
        CloudAPI.getInstance().getEventManager().registerListener(CloudAPI.getInstance().getThisSidesCloudModule(), listener);
    }

}
