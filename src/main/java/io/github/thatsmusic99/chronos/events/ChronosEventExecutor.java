package io.github.thatsmusic99.chronos.events;

import io.github.thatsmusic99.chronos.listeners.ChronosListener;
import io.github.thatsmusic99.chronos.listeners.CustomListener;
import io.github.thatsmusic99.chronos.util.ChronosTimingsHandler;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

import java.lang.reflect.InvocationTargetException;

public class ChronosEventExecutor implements EventExecutor {

    private final Class<? extends Event> listeningClass;
    private final String listenerName;

    public ChronosEventExecutor(String className, String name) throws ClassNotFoundException {
        listeningClass = Class.forName(className).asSubclass(Event.class);
        listenerName = name;
    }

    @Override
    public void execute(Listener listener, Event event) throws EventException {
        if (listeningClass.isAssignableFrom(event.getClass()) && listener instanceof ChronosListener) {
            ChronosListener customListener = (ChronosListener) listener;
            try {
                customListener.onEvent(event);
            } catch (Exception e) {
                throw new EventException(e);
            }
        }
    }
}
