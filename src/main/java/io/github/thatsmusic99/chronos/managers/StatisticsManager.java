package io.github.thatsmusic99.chronos.managers;

import io.github.thatsmusic99.chronos.api.Statistic;

import java.util.HashMap;

public class StatisticsManager {

    private static HashMap<String, Statistic> statsMap = new HashMap<>();

    public static void registerStat(String name, String displayName) {
        if (!statsMap.containsKey(name)) {
            statsMap.put(name, new Statistic(displayName, name));
        }
    }

    public static String getDisplayName(String id) {
        if (!statsMap.containsKey(id)) return "";
        return statsMap.get(id).getDisplayName();
    }
}
