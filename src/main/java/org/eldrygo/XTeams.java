package org.eldrygo;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.eldrygo.Commands.XTeamsCommand;
import org.eldrygo.Extensions.XTeamsExpansion;
import org.eldrygo.Managers.ConfigManager;
import org.eldrygo.Managers.TeamManager;
import org.eldrygo.Utils.ChatUtils;
import org.eldrygo.Utils.LogsUtils;

import java.io.File;

public class XTeams extends JavaPlugin {
    public String version;
    public String prefix;
    public FileConfiguration messagesConfig;
    private ChatUtils chatUtils;
    public LogsUtils logsUtils;
    public TeamManager teamManager;
    public ConfigManager configManager;
    private boolean workingPlaceholderAPI = false;

    @Override
    public void onEnable() {
        this.version = getDescription().getVersion(); // Establecer versión dentro de onEnable
        this.configManager = new ConfigManager(this);
        this.messagesConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "messages.yml"));
        this.chatUtils = new ChatUtils(this, configManager);
        this.logsUtils = new LogsUtils(this);
        this.teamManager = new TeamManager(this);
        configManager.loadConfig();
        loadPlaceholderAPI();
        configManager.loadMessages();
        configManager.loadTeamsFromConfig();
        loadCommands();
        logsUtils.sendStartupMessage();
    }

    @Override
    public void onDisable() {
        configManager.saveTeamsToConfig();
        logsUtils.sendShutdownMessage();
    }

    public ConfigManager getConfigManager() { return configManager; }
    public TeamManager getTeamManager() { return this.teamManager; }
    public ChatUtils getChatUtils() { return chatUtils; }
    public boolean isPlaceholderAPIEnabled() {
        return workingPlaceholderAPI;
    }
    private void loadCommands() {
        if (getCommand("xteams") == null) {
            getLogger().severe("❌ Error: xTeams command is not registered in plugin.yml");
        } else {
            getCommand("xteams").setExecutor(new XTeamsCommand(this));
        }
    }
    private void loadPlaceholderAPI() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new XTeamsExpansion(this).register();
            getLogger().info("✅ PlaceholderAPI detected. Placeholders will work.");
            workingPlaceholderAPI = true;
        } else {
            getLogger().warning("⚠ PlaceholderAPI not detected. Placeholders will not work.");
        }
    }
}