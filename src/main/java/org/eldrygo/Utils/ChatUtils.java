package org.eldrygo.Utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.eldrygo.Managers.ConfigManager;
import org.eldrygo.XTeams;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtils {
    private final XTeams plugin;
    private ConfigManager configManager;

    public ChatUtils(XTeams plugin) {
        this.plugin = plugin;
    }

    public static String formatColor(String message) {
        message = replaceHexColors(message);
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private static String replaceHexColors(String message) {
        Pattern hexPattern = Pattern.compile("#([A-Fa-f0-9]{6})");
        Matcher matcher = hexPattern.matcher(message);
        StringBuffer buffer = new StringBuffer();

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
    public String getMessage(String path) {
        String message = plugin.messagesConfig.getString(path);
        if (plugin.messagesConfig.isList(path)) {
            List<String> lines = plugin.messagesConfig.getStringList(path);
            return ChatUtils.formatColor(String.join("\n", lines));
        } else {
            if (message == null || message.isEmpty()) {
                plugin.getLogger().warning("[WARNING] Message not found: " + path);
                return ChatUtils.formatColor("&r %prefix% #FF0000&l[ERROR] #FF3535Message not found: " + path).replace("%prefix%", configManager.getPrefix());
            }
            return ChatUtils.formatColor(message.replace("%prefix%", configManager.getPrefix()));
        }
    }
    public List<String> getMessageList(String path) {
        List<String> messages = plugin.getConfig().getStringList(path);
        if (messages == null) {
            return new ArrayList<>();
        }
        return messages;
    }
    public FileConfiguration getMessageConfig() {
        return plugin.messagesConfig;
    }
}