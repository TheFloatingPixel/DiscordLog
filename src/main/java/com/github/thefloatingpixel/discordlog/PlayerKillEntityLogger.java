package com.github.thefloatingpixel.discordlog;

import com.github.thefloatingpixel.discordlog.util.DiscordWebhookUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;
import static net.andreinc.aleph.AlephFormatter.str;

public class PlayerKillEntityLogger implements Listener {

    Plugin plugin;

    public PlayerKillEntityLogger(Plugin p) {
        this.plugin = p;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        var entity = event.getEntity();
        if (event.getEntity().getKiller() != null) {
            var p = event.getEntity().getKiller();
            onPlayerKillEntity(p, entity);
            if (entity.getCustomName() != null) {
                onPlayerKillNamedEntity(p, entity);
            }
        }
    }

    private void onPlayerKillEntity(Player p, Entity e) {
        if (!plugin.getConfig().getStringList("logged-events").contains("player-kill-entity")) {
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            for (String url : plugin.getConfig().getStringList("webhooks")) {
                var text = str(plugin.getConfig().getString("messages.player-kill-entity"))
                        .arg("player", p.getDisplayName())
                        .arg("type", e.getType())
                        .fmt();

                DiscordWebhookUtils.sendMessage(url, text);
            }
        });
    }

    private void onPlayerKillNamedEntity(Player p, Entity e) {
        if (!plugin.getConfig().getStringList("logged-events").contains("player-kill-named-entity")) {
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            for (String url : plugin.getConfig().getStringList("webhooks")) {
                var text = str(plugin.getConfig().getString("messages.player-kill-named-entity"))
                        .arg("player", p.getDisplayName())
                        .arg("name", e.getCustomName())
                        .arg("type", e.getType())
                        .fmt();

                DiscordWebhookUtils.sendMessage(url, text);
            }
        });
    }
}
