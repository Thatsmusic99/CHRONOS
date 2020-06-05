package io.github.thatsmusic99.chronos.events;

import io.github.thatsmusic99.chronos.listeners.CustomListener;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

import java.lang.reflect.InvocationTargetException;

public class ChronosEventExecutor implements EventExecutor {

    private Class<? extends Event> listeningClass;

    public ChronosEventExecutor(String className) throws ClassNotFoundException {
        listeningClass = Class.forName(className).asSubclass(Event.class);
    }

    @Override
    public void execute(Listener listener, Event event) throws EventException {
        if (listeningClass.isAssignableFrom(event.getClass()) && listener instanceof CustomListener) {
            CustomListener customListener = (CustomListener) listener;
            try {
                customListener.onEvent(event);
            } catch (Exception e) {
                throw new EventException(e);
            }
        }
    }
}
