package io.github.thatsmusic99.chronos.configuration;

import io.github.thatsmusic99.chronos.Chronos;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Logger;

public abstract class ConfigSettings {

    protected FileConfiguration config;
    protected File configF;
    public String conName = "";
    protected Chronos chronos;

    protected void enable() {
        load();
        reload();
    }

    public void load() {
        if (configF == null || !configF.exists()) {
            configF = new File(Chronos.getInstance().getDataFolder(), conName + ".yml");
            File dir = Chronos.getInstance().getDataFolder();
            if (!dir.exists()) {
                dir.mkdir();
            }
            try {
                if (!configF.exists()) {
                    InputStream file = Chronos.getInstance().getResource(conName + ".yml");
                    Files.copy(file, new File(Chronos.getInstance().getDataFolder(), conName + ".yml").toPath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void reload() {
        try {
            config = new YamlConfiguration();
            config.load(configF);
        } catch (InvalidConfigurationException ex) {
            Logger logger = Chronos.getInstance().getLogger();
            logger.severe("There is a configuration error in the plugin configuration files! Details below:");
            logger.severe(ex.getMessage());
            logger.severe("We have renamed the faulty configuration to " + conName + "-errored.yml for you to inspect.");
            configF.renameTo(new File(Chronos.getInstance().getDataFolder(), conName + "-errored.yml"));
            logger.severe("When you believe you have fixed the problems, change the file name back to " + conName + ".yml and reload the configuration.");
            logger.severe("If you are unsure, please contact the developer (Thatsmusic99).");
            load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }

}
