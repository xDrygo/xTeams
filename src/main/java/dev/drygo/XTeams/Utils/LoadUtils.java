package dev.drygo.XTeams.Utils;

import dev.drygo.XTeams.API.XTeamsAPI;
import dev.drygo.XTeams.Commands.XTeamsCommand;
import dev.drygo.XTeams.Commands.XTeamsTabCompleter;
import dev.drygo.XTeams.Hooks.Listeners.TeamListener;
import dev.drygo.XTeams.Hooks.PlaceholderAPI.XTeamsExpansion;
import dev.drygo.XTeams.Managers.ConfigManager;
import dev.drygo.XTeams.XTeams;
import org.bukkit.Bukkit;

import java.util.Objects;

public class LoadUtils {
    private final XTeams plugin;
    private final ConfigManager configManager;

    public LoadUtils(XTeams plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    public void loadFeatures() {
        XTeamsAPI.init(plugin.getTeamManager());
        loadFiles();
        loadCommand();
        loadListeners();
        loadPlaceholderAPI();
        loadHooks();
    }

    public void loadFiles() {
        configManager.loadConfig();
        configManager.loadMessages();
        configManager.loadTeamsFromConfig();
    }

    private void loadListeners() {
        plugin.getServer().getPluginManager().registerEvents(new TeamListener(plugin), plugin);
    }

    private void loadCommand() {
        if (plugin.getCommand("xteams") == null) {
            plugin.getLogger().severe("❌ Error: xTeams command is not registered in plugin.yml");
        } else {
            Objects.requireNonNull(plugin.getCommand("xteams")).setExecutor(new XTeamsCommand(plugin));
            Objects.requireNonNull(plugin.getCommand("xteams")).setTabCompleter(new XTeamsTabCompleter(plugin));
        }
    }
    private void loadPlaceholderAPI() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new XTeamsExpansion(plugin).register();
            plugin.getLogger().info("✅ PlaceholderAPI detected. Placeholders will work.");
            plugin.setWorkingPlaceholderAPI(true);
        } else {
            plugin.getLogger().warning("⚠ PlaceholderAPI not detected. Placeholders will not work.");
        }
    }
    private void loadHooks() {
        if (plugin.getConfig().getBoolean("hooks.luckperms.enabled", false)) {
            if (Bukkit.getPluginManager().getPlugin("LuckPerms") != null) {
                plugin.getLogger().info("✅ LuckPerms detected. LuckPerms sync hook successfully enabled.");
                plugin.setEnabledLuckPermsHook(true);
            } else {
                plugin.getLogger().warning("⚠ LuckPerms not detected. Can't load LuckPerms sync hook.");
            }
        }
        if (plugin.getConfig().getBoolean("hooks.minecraft_team.enabled", false)) {
            plugin.getLogger().info("✅ Minecraft Team sync hook successfully enabled.");
            plugin.setEnabledMinecraftTeamHook(true);
        }
        if (plugin.getConfig().getBoolean("hooks.auto_team.enabled", false)) {
            plugin.getLogger().info("✅ Auto Team hook successfully enabled.");
            plugin.setEnabledAutoTeam(true);
        }
    }
}
