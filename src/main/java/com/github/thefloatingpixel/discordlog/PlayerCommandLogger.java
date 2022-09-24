package com.github.thefloatingpixel.discordlog;

import com.github.thefloatingpixel.discordlog.util.DiscordWebhookUtils;
import net.andreinc.aleph.AlephFormatter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;
import static net.andreinc.aleph.AlephFormatter.str;

public class PlayerCommandLogger implements Listener {

    private Plugin plugin;

    public PlayerCommandLogger(Plugin p) {
        plugin = p;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        var config = plugin.getConfig();
        var loggedEvents = config.getStringList("logged-events");
        if (!loggedEvents.contains("player-command-execution") && !loggedEvents.contains("command-execution")) {
            return;
        }
        if (event.getMessage().matches("[ ]+")) return;

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
           for (String url : plugin.getConfig().getStringList("webhooks")) {
               var text = str(config.getString("messages.player-command"))
                       .arg("player", event.getPlayer().getDisplayName())
                       .arg("command", event.getMessage())
                       .fmt();
               var msg = DiscordWebhookUtils.sendMessage(url, text);
               if (!msg) {
                    plugin.getLogger().warning("Sending discord message failed!");
               }
           }
        });
    }
}
