package io.github.thatsmusic99.chronos.util;

public interface ChronosCall {
    void onSuccess(long data);

    default void onFail() {}
}
