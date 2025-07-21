package dev.drygo.XTeams.Utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import dev.drygo.XTeams.Managers.ConfigManager;
import dev.drygo.XTeams.XTeams;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtils {
    private final XTeams plugin;
    private final ConfigManager configManager;

    public ChatUtils(XTeams plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    public static String formatColor(String message) {
        message = replaceHexColors(message);
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private static String replaceHexColors(String message) {
        Pattern hexPattern = Pattern.compile("#([A-Fa-f0-9]{6})");
        Matcher matcher = hexPattern.matcher(message);
        StringBuilder buffer = new StringBuilder();

        while (matcher.find()) {
            String hexColor = matcher.group(1);
            StringBuilder color = new StringBuilder("&x");
            for (char c : hexColor.toCharArray()) {
                color.append("&").append(c);
            }
            matcher.appendReplacement(buffer, color.toString());
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }
    public String getMessage(String path, OfflinePlayer player) {
        if (configManager == null) {
            throw new IllegalStateException("ConfigManager no está inicializado.");
        }

        String message = getMessageConfig().isList(path)
                ? String.join("\n", getMessageConfig().getStringList(path))
                : getMessageConfig().getString(path);

        if (message == null || message.isEmpty()) {
            plugin.getLogger().warning("[WARNING] Message not found: " + path);
            return ChatUtils.formatColor("&r" + configManager.getPrefix() + " #FF0000&l[ERROR] #FF3535Message not found: " + path);
        }

        // Reemplazar placeholders
        if (player != null) {
            message = message.replace("%player%", Objects.requireNonNull(player.getName()));
        } else {
            message = message.replace("%player%", "Unknown");
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            message = PlaceholderAPI.setPlaceholders(player, message);
        }

        message = message.replace("%prefix%", configManager.getPrefix());

        return ChatUtils.formatColor(message);
    }
    public List<String> getMessageList(String path) {
        return getMessageConfig().getStringList(path);
    }
    public FileConfiguration getMessageConfig() {
        return plugin.messagesConfig;
    }

}