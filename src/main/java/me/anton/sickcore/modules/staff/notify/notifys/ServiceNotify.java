package me.anton.sickcore.modules.staff.notify.notifys;

import eu.thesimplecloud.api.event.service.CloudServiceRegisteredEvent;
import eu.thesimplecloud.api.event.service.CloudServiceStartedEvent;
import eu.thesimplecloud.api.event.service.CloudServiceUnregisteredEvent;
import eu.thesimplecloud.api.eventapi.CloudEventHandler;
import eu.thesimplecloud.api.eventapi.IListener;
import me.anton.sickcore.modules.staff.Staff;
import me.anton.sickcore.modules.staff.notify.NotifyType;

public class ServiceNotify implements IListener {

    @CloudEventHandler
    public void handle(CloudServiceRegisteredEvent event){
        new Staff().notify(NotifyType.SERVICE,"§e⚙ §7"+ event.getCloudService().getName() + " is now queued!");
    }

    @CloudEventHandler
    public void handle(CloudServiceStartedEvent event){
        new Staff().notify(NotifyType.SERVICE,"§2✔ §7"+ event.getCloudService().getName() + " has started!");
    }

    @CloudEventHandler
    public void handle(CloudServiceUnregisteredEvent event){
        new Staff().notify(NotifyType.SERVICE ,"§4✖ §7"+ event.getCloudService().getName() + " has stopped!");
    }
}
