package com.github.thefloatingpixel.discordlog;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiscordLogTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("discordlog")) return null;
        if (args.length > 1) return null;
        Bukkit.getLogger().info(Arrays.toString(args));

        return Arrays.asList("reload-config", "test-webhooks", "info");
    }

}
