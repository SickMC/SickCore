package me.anton.sickcore.api.handler.listeners.bukkit.events.world;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitEventProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeEventHandler extends BukkitEventProvider<WeatherChangeEvent> {
    @EventHandler
    public void handleEvent(WeatherChangeEvent event) {
        BukkitListenerProvider provider = BukkitCore.getInstance().getProvider();
        provider.iterator(run -> run.onWeatherChange(event.getWorld(), event));
    }
}
