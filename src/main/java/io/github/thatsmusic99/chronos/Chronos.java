package io.github.thatsmusic99.chronos;

import io.github.thatsmusic99.chronos.configuration.MainConfiguration;
import io.github.thatsmusic99.chronos.events.ChronosEventExecutor;
import io.github.thatsmusic99.chronos.listeners.CustomListener;
import io.github.thatsmusic99.chronos.sql.SQLHandler;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class Chronos extends JavaPlugin {

    private static Chronos instance;
    private MainConfiguration config;
    private SQLHandler sql;

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        instance = this;
        config = new MainConfiguration(this);
        sql = new SQLHandler();
        for (String str : Arrays.asList(ChatColor.AQUA + " _____ _   _______ _____ _   _ _____ _____ ",
                ChatColor.AQUA + "/  __ \\ | | | ___ \\  _  | \\ | |  _  /  ___|",
                ChatColor.AQUA + "| /  \\/ |_| | |_/ / | | |  \\| | | | \\ `--. ",
                ChatColor.DARK_AQUA + "| |   |  _  |    /| | | | . ` | | | |`--. \\",
                ChatColor.DARK_AQUA + "| \\__/\\ | | | |\\ \\\\ \\_/ / |\\  \\ \\_/ /\\__/ /",
                ChatColor.DARK_AQUA + " \\____|_| |_|_| \\_|\\___/\\_| \\_/\\___/\\____/ ",
                "",
                ChatColor.GRAY + "                          by Thatsmusic99")) {
            getServer().getConsoleSender().sendMessage(str);
        }
        getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "CHRONOS " + ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "CHRONOS is enabled!");
        //
    /*    new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    getServer().getPluginManager().registerEvent((Class<? extends Event>) Class.forName("org.bukkit.event.player.PlayerJoinEvent"),
                            new CustomListener(),
                            EventPriority.LOW,
                            new ChronosEventExecutor("org.bukkit.event.player.PlayerJoinEvent"), Chronos.getInstance());
                } catch (ClassNotFoundException e) {
                    getLogger().severe("Class org.bukkit.player.PlayerJoinEvent not found!");
                } catch (ClassCastException e) {
                    getLogger().severe("Class org.bukkit.player.PlayerJoinEvent is not an event!");
                }
            }
        }.runTaskAsynchronously(this); */
    }

    public static Chronos getInstance() {
        return instance;
    }
}
