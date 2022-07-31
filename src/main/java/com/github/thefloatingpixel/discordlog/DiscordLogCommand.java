package com.github.thefloatingpixel.discordlog;

import com.github.thefloatingpixel.discordlog.util.DiscordWebhookUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class DiscordLogCommand implements CommandExecutor {

    DiscordLog plugin;

    public DiscordLogCommand(DiscordLog p) {
        plugin = p;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) return false;

        switch (args[0]) {
            case "reload-config":
                if (sender.hasPermission("discordlog.reloadconfig")) {
                    plugin.getLogger().info(plugin.getConfig().getStringList("logged-events").toString());
                    plugin.reloadConfig();
                    plugin.getLogger().info(plugin.getConfig().getStringList("logged-events").toString());
                    plugin.validateConfig();
                    plugin.getLogger().info(plugin.getConfig().getStringList("logged-events").toString());
                    sender.sendMessage(ChatColor.GREEN + "[DiscordLog] The config was reloaded successfully");
                } else {
                    sender.sendMessage(ChatColor.RED + "You don't have permission to use this command");
                }
                break;
            case "test-webhooks":
                if (sender.hasPermission("discordlog.testwebhooks")) {
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                        for (String url : plugin.getConfig().getStringList("webhooks")) {
                            DiscordWebhookUtils.sendMessage(url, "This is a test message.");
                        }
                    });
                } else {
                    sender.sendMessage(ChatColor.RED + "You don't have permission to use this command");
                }
                break;
            case "info":
                sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "DiscordLog v.1.0" + ChatColor.RESET +
                        ChatColor.DARK_GREEN + " >" +ChatColor.GREEN+ " by TheFloatingPixel" +
                        ChatColor.DARK_GREEN + " >" +ChatColor.GREEN+ " Github (https://github.com/TheFloatingPixel/DiscordLog)");
                break;
            default:
                return false;
        }

        return true;
    }
}
