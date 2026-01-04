package dev.drygo.XTeams.Hooks.PlaceholderAPI;

import dev.drygo.XTeams.Managers.TeamManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import dev.drygo.XTeams.XTeams;
import dev.drygo.XTeams.Models.Team;
import org.jetbrains.annotations.NotNull;

public class XTeamsExpansion extends PlaceholderExpansion {

    private final XTeams plugin;

    public XTeamsExpansion(XTeams plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "xteams";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Drygo";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (params.startsWith("hasplayer_")) {
            String[] parts = params.replace("hasplayer_", "").split(":");
            if (parts.length == 2) {
                String teamName = parts[0];
                String targetPlayerName = parts[1];
                Team team = TeamManager.getTeam(teamName);
                return (team != null && team.hasMember(targetPlayerName)) ? "true" : "false";
            }
        }

        if (params.startsWith("teamname_")) {
            String targetPlayer = params.replace("teamname_", "");
            OfflinePlayer target = Bukkit.getOfflinePlayer(targetPlayer);
            return getTeamWithHighestPriority(target, true);
        }

        if (params.startsWith("teamdisplayname_")) {
            String targetPlayer = params.replace("teamdisplayname_", "");
            OfflinePlayer target = Bukkit.getOfflinePlayer(targetPlayer);
            return getTeamWithHighestPriority(target, false);
        }

        if (params.startsWith("playercount_")) {
            String teamName = params.replace("playercount_", "");
            Team team = TeamManager.getTeam(teamName);
            return team != null ? String.valueOf(team.getMembers().size()) : "null";
        }

        if (params.startsWith("teammembers_")) {
            String teamName = params.replace("teammembers_", "");
            Team team = TeamManager.getTeam(teamName);
            return team != null ? String.join(", ", team.getMembers()) : "null";
        }

        if (params.equals("teams")) {
            return String.join(", ", TeamManager.listTeams());
        }

        if (params.startsWith("teamexists_")) {
            String teamName = params.replace("teamexists_", "");
            return TeamManager.teamExists(teamName) ? "true" : "false";
        }

        if (params.startsWith("teampriority_")) {
            String teamName = params.replace("teampriority_", "");
            Team team = TeamManager.getTeam(teamName);
            return team != null ? String.valueOf(team.getPriority()) : "null";
        }

        return null;
    }
    private String getTeamWithHighestPriority(OfflinePlayer player, boolean returnName) {
        Team highestPriorityTeam = null;
        int highestPriority = Integer.MIN_VALUE;

        for (String teamName : TeamManager.listTeams()) {
            Team team = TeamManager.getTeam(teamName);

            if (team != null && team.hasMember(player.getName())) {
                if (team.getPriority() > highestPriority) {
                    highestPriority = team.getPriority();
                    highestPriorityTeam = team;
                }
            }
        }

        if (highestPriorityTeam != null) {
            return returnName ? highestPriorityTeam.getName() : highestPriorityTeam.getDisplayName();
        }

        return "null";
    }
}
