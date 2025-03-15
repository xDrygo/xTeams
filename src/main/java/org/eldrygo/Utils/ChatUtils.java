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
        if (configManager == null) {
            throw new IllegalStateException("ConfigManager no está inicializado.");
        }
        String message = getMessageConfig().getString(path);
        if (getMessageConfig().isList(path)) {
            List<String> lines = getMessageConfig().getStringList(path);
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
        // Accede a la instancia de messagesConfig directamente
        List<String> messages = getMessageConfig().getStringList(path);
        if (messages == null) {
            return new ArrayList<>();  // Devuelve una lista vacía si no se encuentra el mensaje
        }
        return messages;  // Retorna la lista de mensajes
    }
    public FileConfiguration getMessageConfig() {
        return plugin.messagesConfig;
    }

}