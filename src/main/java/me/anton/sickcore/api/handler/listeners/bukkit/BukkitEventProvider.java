package me.anton.sickcore.api.handler.listeners.bukkit;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;

public abstract class BukkitEventProvider<T extends Event> implements Listener {

    public abstract void handleEvent(T event);

}
