package dev.drygo.XTeams.Hooks.Minecraft.Managers;

import dev.drygo.XTeams.XTeams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;

public class MinecraftTeamManager {

    private static XTeams plugin;
    private static final Map<String, String> teamGroupMap = new HashMap<>();
    private static Scoreboard scoreboard;

    public static void init(XTeams plugin) {
        MinecraftTeamManager.plugin = plugin;
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        loadFromConfig();
    }

    private static void loadFromConfig() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("hooks.minecraft_team.team_groups");
        if (section != null) {
            for (String teamName : section.getKeys(false)) {
                String internalTeamId = section.getString(teamName);
                if (internalTeamId != null && !internalTeamId.isBlank()) {
                    teamGroupMap.put(teamName.toLowerCase(), internalTeamId);
                }
            }
        }
    }

    public static void applyGroup(Player player, String teamName) {
        handleTeamChange(player, teamName, true);
    }

    public static void removeGroup(Player player, String teamName) {
        handleTeamChange(player, teamName, false);
    }

    private static void handleTeamChange(Player player, String teamName, boolean add) {
        String teamKey = teamName.toLowerCase();
        String teamId = teamGroupMap.get(teamKey);

        if (teamId == null || teamId.isBlank()) {
            plugin.getLogger().warning("❌ No scoreboard team configured for team: " + teamName);
            return;
        }

        Team team = scoreboard.getTeam(teamId);
        if (team == null && add) {
            team = scoreboard.registerNewTeam(teamId);
            team.setDisplayName(ChatColor.translateAlternateColorCodes('&', teamName));
        }

        if (add) {
            // Remover de cualquier otro equipo
            for (Team otherTeam : scoreboard.getTeams()) {
                otherTeam.removeEntry(player.getName());
            }
            team.addEntry(player.getName());
        } else {
            if (team != null) {
                team.removeEntry(player.getName());
            }
        }
    }
}