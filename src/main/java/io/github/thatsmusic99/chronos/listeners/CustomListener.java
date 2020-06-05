package io.github.thatsmusic99.chronos.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;

// I'm breaking so many rules here and I hate it
public class CustomListener implements Listener {

    public void onEvent(Event event) {
        System.out.println("Ping!");
    }
}
