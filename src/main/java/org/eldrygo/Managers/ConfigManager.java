package org.eldrygo.Managers;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.eldrygo.Models.Team;
import org.eldrygo.Utils.ChatUtils;
import org.eldrygo.XTeams;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;

public class ConfigManager {
    private final XTeams plugin;

    public ConfigManager(XTeams plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();
    }
    public String getPrefix() { return plugin.prefix; }
    public FileConfiguration getMessageConfig() { return plugin.messagesConfig; }

    public void loadMessages() {
        File messagesFile = new File(plugin.getDataFolder(), "messages.yml");

        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
            plugin.getLogger().info("✅ The messages.yml file did not exist, it has been created.");
        } else {
            plugin.getLogger().info("✅ The messages.yml file has been loaded successfully.");
        }
        plugin.prefix = ChatUtils.formatColor(getMessageConfig().getString("prefix", "#ffbaff&lX&r&lTeams &cDefault Prefix &8»&r"));
        plugin.messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }
    public void loadTeamsFromConfig() {
        for (String teamName : plugin.getConfig().getConfigurationSection("teams").getKeys(false)) {
            Set<Player> members = plugin.getConfig().getStringList("teams." + teamName).stream()
                    .map(Bukkit::getPlayer)
                    .filter(player -> player != null && player.isOnline())
                    .collect(Collectors.toSet());
            plugin.team.teams.put(teamName, (Team) members);
        }
    }
}
