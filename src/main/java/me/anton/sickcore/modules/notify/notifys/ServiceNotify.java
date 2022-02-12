package me.anton.sickcore.modules.notify.notifys;

import eu.thesimplecloud.api.event.service.CloudServiceRegisteredEvent;
import eu.thesimplecloud.api.event.service.CloudServiceStartedEvent;
import eu.thesimplecloud.api.event.service.CloudServiceUnregisteredEvent;
import eu.thesimplecloud.api.eventapi.CloudEventHandler;
import eu.thesimplecloud.api.eventapi.IListener;
import me.anton.sickcore.modules.notify.NotifyAction;
import me.anton.sickcore.modules.notify.NotifyType;

public class ServiceNotify implements IListener {

    @CloudEventHandler
    public void handle(CloudServiceRegisteredEvent event){
        new NotifyAction("§e⚙ §7"+ event.getCloudService().getName() + " is now queued!", NotifyType.SERVICE);
    }

    @CloudEventHandler
    public void handle(CloudServiceStartedEvent event){
        new NotifyAction("§2✔ §7"+ event.getCloudService().getName() + " has started!", NotifyType.SERVICE);
    }

    @CloudEventHandler
    public void handle(CloudServiceUnregisteredEvent event){
        new NotifyAction("§4✖ §7"+ event.getCloudService().getName() + " has stopped!", NotifyType.SERVICE);
    }
}
