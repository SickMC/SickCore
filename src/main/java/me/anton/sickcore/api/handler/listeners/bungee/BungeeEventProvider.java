package me.anton.sickcore.api.handler.listeners.bungee;

import net.md_5.bungee.api.plugin.Event;
import net.md_5.bungee.api.plugin.Listener;

public abstract class BungeeEventProvider<T extends Event> implements Listener {

    public abstract void handleEvent(T event);

}
