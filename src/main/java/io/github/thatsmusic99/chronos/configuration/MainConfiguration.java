package io.github.thatsmusic99.chronos.configuration;

import io.github.thatsmusic99.chronos.Chronos;

public class MainConfiguration extends ConfigSettings {

    public MainConfiguration(Chronos chronos) {
        this.chronos = chronos;
        this.conName = "config";
        enable();
    }
}
