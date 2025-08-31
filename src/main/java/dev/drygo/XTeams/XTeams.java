package dev.drygo.XTeams;

import dev.drygo.XTeams.Hooks.AutoTeam.Managers.AutoTeamManager;
import dev.drygo.XTeams.Hooks.LuckPerms.Managers.LuckPermsGroupManager;
import dev.drygo.XTeams.Hooks.Minecraft.Managers.MinecraftTeamManager;
import dev.drygo.XTeams.Utils.LoadUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import dev.drygo.XTeams.Managers.ConfigManager;
import dev.drygo.XTeams.Managers.TeamManager;
import dev.drygo.XTeams.Utils.ChatUtils;
import dev.drygo.XTeams.Utils.LogsUtils;

import java.io.File;

public class XTeams extends JavaPlugin {
    public String version;
    public String prefix;
    public FileConfiguration messagesConfig;
    private ChatUtils chatUtils;
    public LogsUtils logsUtils;
    public LoadUtils loadUtils;

    public TeamManager teamManager;
    public LuckPermsGroupManager luckPermsGroupManager;
    public MinecraftTeamManager minecraftTeamManager;
    public AutoTeamManager autoTeamManager;
    public ConfigManager configManager;

    private boolean workingPlaceholderAPI = false;
    private boolean enabledLuckPermsHook = false;
    private boolean enabledMinecraftTeamHook = false;
    private boolean enabledAutoTeam = false;

    @Override
    public void onEnable() {
        this.version = getDescription().getVersion(); // Establecer versión dentro de onEnable
        this.configManager = new ConfigManager(this);
        this.messagesConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "messages.yml"));
        this.chatUtils = new ChatUtils(this, configManager);
        this.logsUtils = new LogsUtils(this);
        this.loadUtils = new LoadUtils(this, configManager);
        this.teamManager = new TeamManager();
        loadUtils.loadFeatures();
        logsUtils.sendStartupMessage();
    }

    @Override
    public void onDisable() {
        configManager.saveTeamsToConfig();
        logsUtils.sendShutdownMessage();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
    public TeamManager getTeamManager() {
        return teamManager;
    }
    public ChatUtils getChatUtils() {
        return chatUtils;
    }
    public LuckPermsGroupManager getLuckPermsGroupManager() {
        return luckPermsGroupManager;
    }
    public MinecraftTeamManager getMinecraftTeamManager() {
        return minecraftTeamManager;
    }
    public AutoTeamManager getAutoTeamManager() {
        return autoTeamManager;
    }

    public boolean isWorkingPlaceholderAPI() {
        return workingPlaceholderAPI;
    }
    public void setWorkingPlaceholderAPI(boolean workingPlaceholderAPI) {
        this.workingPlaceholderAPI = workingPlaceholderAPI;
    }
    public boolean isEnabledLuckPermsHook() {
        return enabledLuckPermsHook;
    }
    public void setEnabledLuckPermsHook(boolean enabledLuckPermsHook) {
        this.enabledLuckPermsHook = enabledLuckPermsHook;
    }
    public boolean isEnabledMinecraftTeamHook() {
        return enabledMinecraftTeamHook;
    }
    public void setEnabledMinecraftTeamHook(boolean enabledMinecraftTeamHook) {
        this.enabledMinecraftTeamHook = enabledMinecraftTeamHook;
    }
    public boolean isEnabledAutoTeam() {
        return enabledAutoTeam;
    }
    public void setEnabledAutoTeam(boolean enabledAutoTeam) {
        this.enabledAutoTeam = enabledAutoTeam;
    }
}