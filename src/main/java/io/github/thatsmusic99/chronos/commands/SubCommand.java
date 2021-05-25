package io.github.thatsmusic99.chronos.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.*;
import java.util.List;

public abstract class SubCommand {

    public abstract boolean fire(String[] args, CommandSender sender);

    public abstract List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args);

    @Documented
    @Target(ElementType.TYPE)
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CommandMeta {
        String commandname();
        String permission();
        String subcommand();
        String usage();
        String description();
    }
}
