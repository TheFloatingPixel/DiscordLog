package com.github.thefloatingpixel.discordlog.util;

import org.bukkit.Bukkit;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;

public class DiscordWebhookUtils {
    public static boolean sendMessage(String url, String message) {
        var json = "{\"content\": \""+message+"\"}";

        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        var client = HttpClient.newHttpClient();

        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            Bukkit.getLogger().log(Level.WARNING, "Exception while sending discord webhook message", e);
            return false;
        }
        return true;
    }
}
