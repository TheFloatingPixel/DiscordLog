package com.github.thefloatingpixel.discordlog;

import com.github.thefloatingpixel.discordlog.util.DiscordWebhookUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.Plugin;

public class ConsoleCommandLogger implements Listener {

    private Plugin plugin;

    public ConsoleCommandLogger(Plugin p) {
        plugin = p;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommand(ServerCommandEvent event) {
        var config = plugin.getConfig();
        var loggedEvents = config.getStringList("logged-events");
        if (!loggedEvents.contains("console-command-execution") && !loggedEvents.contains("command-execution")) {
            return;
        }
        if (event.getCommand().matches("[ ]+")) return;

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            for (String url : plugin.getConfig().getStringList("webhooks")) {
                var format = config.getString("messages.console-command");
                assert format != null;
                var text = String.format(format, event.getCommand());
                var msg = DiscordWebhookUtils.sendMessage(url, text);
                if (!msg) {
                    plugin.getLogger().warning("Sending discord message failed!");
                }
            }
        });
    }

}
