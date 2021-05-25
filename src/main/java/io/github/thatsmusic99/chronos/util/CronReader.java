package io.github.thatsmusic99.chronos.util;

import io.github.thatsmusic99.chronos.Chronos;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A Cron expression reader and runner
 * @author Holly (Thatsmusic99)
 */
public class CronReader {

    private final HashSet<CronObject> arguments;
    private BukkitRunnable runnable;

    public CronReader(String expression, CronCall call) {
        arguments = new HashSet<>();
        try {
            String[] parts = expression.split(" ");
            // Seconds
            arguments.add(getCronObject(parts[0], Calendar.SECOND));
            // Minutes
            arguments.add(getCronObject(parts[1], Calendar.MINUTE));
            // Hours
            arguments.add(getCronObject(parts[2], Calendar.HOUR));
            // Day of the month
            arguments.add(getCronObject(parts[3], Calendar.DAY_OF_MONTH));
            // Month
            arguments.add(getCronObject(parts[4], Calendar.MONTH));
            // Day of week
            arguments.add(getCronObject(parts[5], Calendar.DAY_OF_WEEK));
            // Year
            arguments.add(getCronObject(parts[6], Calendar.YEAR));
            runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    Iterator<CronObject> iterator = arguments.iterator();
                    Calendar calendar = Calendar.getInstance();
                    boolean valid = true;
                    while (iterator.hasNext()) {
                        CronObject object = iterator.next();
                        int current = calendar.get(object.unit);
                        switch (object.type) {
                            case RANGE:
                                int lowerBound = object.selectedInts[0];
                                int upperBound = object.selectedInts[1];
                                if (!(current > lowerBound && current < upperBound)) {
                                    valid = false;
                                }
                                break;
                            case SELECTED:
                                valid = false;
                                for (int selInt : object.selectedInts) {
                                    if (selInt == current) {
                                        valid = true;
                                        break;
                                    }
                                }
                                break;
                            case EVERY_AFTER:
                                valid = false;
                                // So let's think this through.
                                // For X/Y, X is the starting point.
                                // So, if current - X >= 0 and current - X % Y == 0
                                int i = current - object.selectedInts[0];
                                if (i >= 0 && i % object.selectedInts[1] == 0) {
                                    valid = true;
                                }
                                break;
                        }
                    }
                    if (valid) {
                        call.onTimeMet();
                    }
                }
            };
        } catch (Exception e) {
            Chronos.getInstance().getLogger().warning("");
        }

    }

    private CronObject getCronObject(String part, int unit) {
        for (ArgumentType type : ArgumentType.values()) {
            if (type.pattern == null) {
                String[] parts = part.split(",");
                Integer[] ints = new Integer[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    ints[i] = strToInt(parts[i]);
                }
                return new CronObject(type, unit, ints);
            } else {
                Matcher matcher = type.pattern.matcher(part);
                Set<Integer> tempSet = new HashSet<>();
                if (matcher.matches()) {
                    tempSet.add(strToInt(matcher.group()));
                }
                if (tempSet.size() == 0) continue;
                Integer[] array = tempSet.toArray(new Integer[0]);
                return new CronObject(type, unit, array);
            }
        }
        return null;
    }

    public void init() {
        runnable.runTaskTimerAsynchronously(Chronos.getInstance(), 0, 20);
    }

    public void terminate() {
        runnable.cancel();
    }

    public static int strToInt(String str) {
        switch (str.toUpperCase()) {
            case "JAN":
                return 0;
            case "SUN":
            case "FEB":
                return 1;
            case "MON":
            case "MAR":
                return 2;
            case "TUE":
            case "APR":
                return 3;
            case "WED":
            case "MAY":
                return 4;
            case "THU":
            case "JUN":
                return 5;
            case "FRI":
            case "JUL":
                return 6;
            case "SAT":
            case "AUG":
                return 7;
            case "SEP":
                return 8;
            case "OCT":
                return 9;
            case "NOV":
                return 10;
            case "DEC":
                return 11;
            default:
                return Integer.parseInt(str);
        }
    }

    private enum ArgumentType {
        ALL(Pattern.compile("^[*?]$")), // *, ?
        EVERY_AFTER(Pattern.compile("^(\\d)+\\/(\\d)+$")), // X/Y
        RANGE(Pattern.compile("^(\\d)+-(\\d)+$")), // X-Y
        SELECTED(null); // X,Y,Z

        Pattern pattern;

        ArgumentType(Pattern pattern) {
            this.pattern = pattern;
        }
    }

    private static class CronObject {
        private ArgumentType type;
        private Integer[] selectedInts;
        private int unit;

        public CronObject(ArgumentType type, int unit, Integer... ints) {
            this.type = type;
            this.unit = unit;
            this.selectedInts = ints;
        }
    }

    public interface CronCall {
        void onTimeMet();
    }
}
