package io.github.thatsmusic99.chronos.api;

import io.github.thatsmusic99.chronos.Chronos;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

/**
 *
 */
public class ChronosPlayer {

    private UUID uuid;
    private static HashMap<UUID, ChronosPlayer> players = new HashMap<>();
    private ChronosRunnable timer;
    private HashMap<String, HashMap<String, Long>> data = new HashMap<>();
    private long lastMovementTime;

    public ChronosPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public void initTimer() {
        final Player player = Bukkit.getPlayer(uuid);
        timer = new ChronosRunnable(this) {
            @Override
            public void run() {
                if (!player.isOnline()) {
                    cancel();
                } else {
                    seconds++;
                }
            }
            public void cancel() {
                update();
                Bukkit.getScheduler().cancelTask(this.getTaskId());
            }
        };
        timer.runTaskTimerAsynchronously(Chronos.getInstance(), 20, 20);
    }

    public static ChronosPlayer getPlayer(UUID uuid) {
        return players.containsKey(uuid) ? players.get(uuid) : new ChronosPlayer(uuid);
    }

    public void resetMovementTime() {
        lastMovementTime = System.currentTimeMillis();
    }

    public abstract static class ChronosRunnable extends BukkitRunnable {
        long seconds = 0;
        long afkSeconds = 0;
        ChronosPlayer chronosPlayer;

        public ChronosRunnable(ChronosPlayer player) {
            this.chronosPlayer = player;
        }

        public void update() {

        }
    }

}
