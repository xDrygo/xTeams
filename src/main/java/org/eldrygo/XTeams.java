package org.eldrygo;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.eldrygo.Commands.XTeamsCommand;
import org.eldrygo.Managers.ConfigManager;
import org.eldrygo.Managers.TeamManager;
import org.eldrygo.Models.Team;
import org.eldrygo.Utils.ChatUtils;
import org.eldrygo.Utils.LogsUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class XTeams extends JavaPlugin {
    public String version = getDescription().getVersion();
    public String prefix;
    public FileConfiguration messagesConfig;
    private ChatUtils chatUtils;
    public LogsUtils logsUtils;
    public TeamManager teamManager;
    public ConfigManager configManager;
    public Team team;
    @Override
    public void onEnable() {
        this.configManager = new ConfigManager(this);
        this.messagesConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "messages.yml"));
        this.chatUtils = new ChatUtils(this);
        this.logsUtils = new LogsUtils(this);
        this.teamManager = new TeamManager(this);
        configManager.loadConfig();
        configManager.loadMessages();
        loadCommands();
        logsUtils.sendStartupMessage();
    }
    public void onDisable() {
        logsUtils.sendShutdownMessage();
    }
    private void loadCommands() {
        getCommand("xteams").setExecutor(new XTeamsCommand(this));
    }
    public ConfigManager getConfigManager() {
        return configManager;
    }
    public TeamManager getTeamManager() { return teamManager; }
    public ChatUtils getChatUtils() { return chatUtils; }
}
