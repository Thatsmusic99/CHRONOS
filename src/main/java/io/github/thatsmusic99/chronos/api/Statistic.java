package io.github.thatsmusic99.chronos.api;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class Statistic {

    private String displayName;
    private String id;

    public Statistic(String displayName, String id) {
        this.displayName = displayName;
        this.id = id;
    }

    public CompletableFuture<String> addPlayerStat(UUID uuid, String... subtypes) {

    }
}
