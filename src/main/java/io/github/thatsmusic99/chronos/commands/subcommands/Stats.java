package io.github.thatsmusic99.chronos.commands.subcommands;

import io.github.thatsmusic99.chronos.commands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Stats extends SubCommand {

    @Override
    public boolean fire(String[] args, CommandSender sender) {
        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
