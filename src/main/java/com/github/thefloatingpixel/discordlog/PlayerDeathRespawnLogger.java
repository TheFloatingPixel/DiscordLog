package com.github.thefloatingpixel.discordlog;

import com.github.thefloatingpixel.discordlog.util.DiscordWebhookUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;

public class PlayerDeathRespawnLogger implements Listener {

    Plugin p;

    public PlayerDeathRespawnLogger(Plugin p) {
        this.p = p;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!p.getConfig().getStringList("logged-events").contains("player-death")) return;

        Bukkit.getScheduler().runTaskAsynchronously(p, () -> {
           for (String url : p.getConfig().getStringList("webhooks")) {
               DiscordWebhookUtils.sendMessage(url, String.format(p.getConfig().getString("messages.player-death"), event.getEntity().getDisplayName()));
           }
        });
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (!p.getConfig().getStringList("logged-events").contains("player-respawn")) return;

        Bukkit.getScheduler().runTaskAsynchronously(p, () -> {
            for (String url : p.getConfig().getStringList("webhooks")) {
                DiscordWebhookUtils.sendMessage(url, String.format(p.getConfig().getString("messages.player-respawn"), event.getPlayer().getDisplayName()));
            }
        });
    }
}
