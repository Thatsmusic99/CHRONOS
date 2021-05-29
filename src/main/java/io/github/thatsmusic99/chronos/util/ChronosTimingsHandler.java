package io.github.thatsmusic99.chronos.util;

import co.aikar.timings.Timings;
import io.github.thatsmusic99.chronos.Chronos;
import org.spigotmc.CustomTimingsHandler;

public class ChronosTimingsHandler {

    private static boolean paper = false;
    private String name;
    private CustomTimingsHandler handler;

    public ChronosTimingsHandler(String name) {
        this.name = name;
    }
    public static void init() {
        Chronos chronos = Chronos.getInstance();
        if (chronos.getServer().getName().equals("Paper")) {
            paper = true;
            chronos.getLogger().info("Successfully hooked with Paper!");
        }
    }

    public void start() {
        if (paper) {
            if (Timings.isTimingsEnabled()) {
                Timings.of(Chronos.getInstance(), name).startTiming();
            }
        } else {
            handler = new CustomTimingsHandler("CHRONOS: " + name);
            handler.startTiming();
        }
    }

    public void finish() {
        if (paper) {
            if (Timings.isTimingsEnabled()) {
                Timings.of(Chronos.getInstance(), name).stopTiming();
            }
        } else {
            handler.stopTiming();
        }
    }
}
