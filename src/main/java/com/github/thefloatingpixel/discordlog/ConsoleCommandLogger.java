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
import static net.andreinc.aleph.AlephFormatter.str;

public class ConsoleCommandLogger implements Listener {

    private Plugin plugin;

    public ConsoleCommandLogger(Plugin p) {
        plugin = p;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onServerCommand(ServerCommandEvent event) {
        var config = plugin.getConfig();
        var loggedEvents = config.getStringList("logged-events");
        if (!loggedEvents.contains("console-command-execution") && !loggedEvents.contains("command-execution")) {
            return;
        }
        if (event.getCommand().matches("[ ]+")) return;

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            for (String url : plugin.getConfig().getStringList("webhooks")) {
                var text = str(plugin.getConfig().getString("messages.console-command"))
                        .arg("command", event.getCommand())
                        .fmt();

                DiscordWebhookUtils.sendMessage(url, text);
            }
        });
    }

}
