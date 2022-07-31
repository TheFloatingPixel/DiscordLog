package com.github.thefloatingpixel.discordlog;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class DiscordLog extends JavaPlugin {

    ConsoleCommandLogger consoleCommandLogger;
    PlayerCommandLogger playerCommandLogger;
    PlayerJoinLeaveLogger playerJoinLeaveLogger;
    PlayerDeathRespawnLogger playerDeathRespawnLogger;
    PlayerKillEntityLogger playerKillEntityLogger;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        consoleCommandLogger = new ConsoleCommandLogger(this);
        getServer().getPluginManager().registerEvents(consoleCommandLogger, this);

        playerCommandLogger = new PlayerCommandLogger(this);
        getServer().getPluginManager().registerEvents(playerCommandLogger, this);

        playerJoinLeaveLogger = new PlayerJoinLeaveLogger(this);
        getServer().getPluginManager().registerEvents(playerJoinLeaveLogger, this);

        playerDeathRespawnLogger = new PlayerDeathRespawnLogger(this);
        getServer().getPluginManager().registerEvents(playerDeathRespawnLogger, this);

        playerKillEntityLogger = new PlayerKillEntityLogger(this);
        getServer().getPluginManager().registerEvents(playerKillEntityLogger, this);

        this.getCommand("discordlog").setExecutor(new DiscordLogCommand(this));
        this.getCommand("discordlog").setTabCompleter(new DiscordLogTabCompleter());

        reloadConfig();

        validateConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public boolean validateConfig() {
        if (this.getConfig().getStringList("webhooks").size() < 1) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.isOp()) {
                    p.sendMessage(ChatColor.RED+"[DiscordLog] WARNING: The discord webhook url" +
                            " is not specified in the config! The plugin will not do anything." +
                            " Once you enter the webhook url in the config, you can use" +
                            " /discordlog reload-config to reload the config");
                }
            }
            return false;
        }

        for (String url : this.getConfig().getStringList("webhooks")) {
            if (!url.matches("https://(discord|discordapp)\\.com/api/webhooks/[0-9]+/[a-zA-Z\\-0-9_]+/?")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.isOp()) {
                        p.sendMessage(ChatColor.RED+"[DiscordLog] WARNING: One or more of your webhook" +
                                " urls is not valid. The plugin will not log anything. Once you fix" +
                                " the invalid url you can use /discordlog reload-config to reload the" +
                                " config.");
                    }
                }
                return false;
            }
        }

        if (getConfig().getStringList("logged-events").size() < 1) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.isOp()) {
                    p.sendMessage(ChatColor.RED+"[DiscordLog] WARNING: No events for logging" +
                            " are specified in the config. This will effectively make the plugin" +
                            " useless.");
                }
            }
        }

        return true;
    }

}
