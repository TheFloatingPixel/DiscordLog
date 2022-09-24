package com.github.thefloatingpixel.discordlog;

import com.github.thefloatingpixel.discordlog.util.DiscordWebhookUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import static net.andreinc.aleph.AlephFormatter.str;

public class PlayerJoinLeaveLogger implements Listener {

    Plugin p;

    public PlayerJoinLeaveLogger(Plugin p) {
        this.p = p;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!p.getConfig().getStringList("logged-events").contains("player-join")) {
            return;
        }

        var text = str(p.getConfig().getString("messages.player-join"))
                .arg("player", e.getPlayer().getDisplayName())
                .fmt();
        log(text);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        if (!p.getConfig().getStringList("logged-events").contains("player-disconnect")) {
            return;
        }

        var text = str(p.getConfig().getString("messages.player-disconnect"))
                .arg("player", e.getPlayer().getDisplayName())
                .fmt();
        log(text);
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent e) {
        if (!p.getConfig().getStringList("logged-events").contains("player-kick")) {
            return;
        }

        var text = str(p.getConfig().getString("messages.player-kick"))
                .arg("player", e.getPlayer().getDisplayName())
                .fmt();
        log(text);
    }

    private void log(String message) {
        Bukkit.getScheduler().runTaskAsynchronously(p, () -> {
           for (String url : p.getConfig().getStringList("webhooks")) {
               DiscordWebhookUtils.sendMessage(url, message);
           }
        });
    }

}
