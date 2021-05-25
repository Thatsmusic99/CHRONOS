package io.github.thatsmusic99.chronos.util;

import io.github.thatsmusic99.chronos.Chronos;

import java.util.HashMap;

public class VariableStripper {

    public static HashMap<String, String> getVariables(String[] args) {
        HashMap<String, String> variables = new HashMap<>();
        for (String str : args) {
            try {
                String[] parts = str.split("=");
                variables.put(parts[0], parts[1]);
            } catch (IndexOutOfBoundsException e) {
                Chronos.getInstance().getLogger().warning("Invalid variable syntax found: " + str + ", must be {var1}={var2}!");
            }
        }
        return variables;
    }
}
