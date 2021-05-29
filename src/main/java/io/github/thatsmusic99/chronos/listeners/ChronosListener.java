package io.github.thatsmusic99.chronos.listeners;

import io.github.thatsmusic99.chronos.managers.ListenerManager;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public class ChronosListener implements Listener {

    private final boolean ignoreIfCancelled;
    private final HashMap<String, List<String>> variableDeclarators;
    private final List<ListenerManager.Action> actions;

    public ChronosListener(ListenerManager.Action action,
                           List<String> playerIdentifier,
                           HashMap<String, List<String>> variables,
                           List<ListenerManager.Action> actions,
                           boolean ignoreIfCancelled) {
        this.ignoreIfCancelled = ignoreIfCancelled;
        this.variableDeclarators = variables;
        this.actions = actions;
    }

    public void onEvent(Event event) {
        HashMap<String, Object> actualVars = new HashMap<>();
        for (String var : variableDeclarators.keySet()) {
            actualVars.put(var, getVariable(event, variableDeclarators.get(var)));
        }

        boolean success = true;

        for (ListenerManager.Action action : actions) {
            if (!ignoreIfCancelled || !(event instanceof Cancellable) || !((Cancellable) event).isCancelled()) {

            }
        }
    }

    private Object getVariable(Event event, List<String> methods) {
        Object fetchingObject = event;
        for (String method : methods) {
            try {
                Method methodObject = fetchingObject.getClass().getMethod(method);
                fetchingObject = methodObject.invoke(fetchingObject);
            } catch (NoSuchMethodException e) {
                System.out.println("No such method, " + method + " in " + fetchingObject.getClass().getName());
                return null;
            } catch (IllegalAccessException e) {
                System.out.println("Cannot access method " + method + " in " + fetchingObject.getClass().getName());
                return null;
            } catch (InvocationTargetException e) {
                System.out.println("Did not invoke method " + method + " in " + fetchingObject.getClass().getName() + " correctly");
                return null;
            }
        }
        return fetchingObject;
    }
}
