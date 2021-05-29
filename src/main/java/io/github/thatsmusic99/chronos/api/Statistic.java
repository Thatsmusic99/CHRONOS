package io.github.thatsmusic99.chronos.api;

public class Statistic {

    private String displayName;
    private String id;

    public Statistic(String displayName, String id) {
        this.displayName = displayName;
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getId() {
        return id;
    }
}
