package io.github.thatsmusic99.chronos.managers;

import io.github.thatsmusic99.chronos.Chronos;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ListenerManager {

    public static void init() {
        File listenersFolder = new File(Chronos.getInstance().getDataFolder(), "listeners");
        if (!listenersFolder.exists()) {
            listenersFolder.mkdirs();
        }
        if (!listenersFolder.isDirectory() || listenersFolder.listFiles() == null) {
            return;
        }
        for (File listenerFile : listenersFolder.listFiles()) {
            YamlConfiguration listenerConfig = YamlConfiguration.loadConfiguration(listenerFile);
            if (!listenerConfig.getBoolean("enabled")) continue;
            // Make sure any plugin required exists
            if (!listenerConfig.getString("required-plugin").isEmpty()) {
                Plugin plugin = Bukkit.getPluginManager().getPlugin(listenerConfig.getString("required-plugin"));
                if (plugin == null || !plugin.isEnabled()) continue;
                if (!listenerConfig.getString("author").isEmpty()) {
                    List<String> authors = Arrays.asList(listenerConfig.getString("author").split(" "));
                    if (!plugin.getDescription().getAuthors().containsAll(authors)) continue;
                }

                Chronos.getInstance().getDescription().getSoftDepend().add(plugin.getName());
            }

            // Register each listener
            for (String key : listenerConfig.getConfigurationSection("events").getKeys(false)) {
                ConfigurationSection eventSection = listenerConfig.getConfigurationSection("events." + key);
                Class<?> eventClass;
                try {
                    eventClass = Class.forName(eventSection.getString("event-class"));
                } catch (ClassNotFoundException ex) {
                    continue;
                }

                List<String> playerIdentifier = eventSection.getStringList("player-identifier");
                // Variable setup
                HashMap<String, List<String>> variables = new HashMap<>();
                for (String varKey : eventSection.getConfigurationSection("variables").getKeys(false)) {
                    variables.put(varKey, eventSection.getStringList("variables." + varKey));
                }
                // Action setup
                List<Action> actions = new ArrayList<>();
                for (String actionStr : eventSection.getStringList("on-success-actions")) {
                    actions.add(new Action(actionStr));
                }
            }
        }
    }

    public static class Action {

        private ActionType actionType;
        private String modifier;

        public Action(String str) {
            String[] parts = str.split(" ");
            actionType = ActionType.valueOf(parts[0].toUpperCase());
            modifier = parts[1];
        }

        public enum ActionType {
            ADD,
            SUBTRACT,
            MULTIPLY,
            DIVIDE,
            SET,
            RESET,
            CANCEL
        }
    }
}
