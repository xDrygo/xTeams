package org.eldrygo.XTeams.Extensions;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.eldrygo.XTeams.XTeams;
import org.eldrygo.XTeams.Models.Team;
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
        // Placeholder %xteams_hasplayer_<team>:<player>%
        if (params.startsWith("hasplayer_")) {
            String[] parts = params.replace("hasplayer_", "").split(":");
            if (parts.length == 2) {
                String teamName = parts[0];
                String targetPlayerName = parts[1];
                Team team = plugin.getTeamManager().getTeam(teamName);
                OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(targetPlayerName);
                return (team != null && team.hasMember(targetPlayer)) ? "true" : "false";
            }
        }

        // Placeholder %xteams_teamname_<player>%
        if (params.startsWith("teamname_")) {
            String targetPlayer = params.replace("teamname_", "");
            OfflinePlayer target = Bukkit.getOfflinePlayer(targetPlayer);
            return getTeamWithHighestPriority(target, true);
        }

        // Placeholder %xteams_teamdisplayname_<player>%
        if (params.startsWith("teamdisplayname_")) {
            String targetPlayer = params.replace("teamdisplayname_", "");
            OfflinePlayer target = Bukkit.getOfflinePlayer(targetPlayer);
            return getTeamWithHighestPriority(target, false);
        }

        // Placeholder %xteams_playercount_<team>%
        if (params.startsWith("playercount_")) {
            String teamName = params.replace("playercount_", "");
            Team team = plugin.getTeamManager().getTeam(teamName);
            return team != null ? String.valueOf(team.getMembers().size()) : "null";
        }

        // Placeholder %xteams_teammembers_<team>%
        if (params.startsWith("teammembers_")) {
            String teamName = params.replace("teammembers_", "");
            Team team = plugin.getTeamManager().getTeam(teamName);
            return team != null ? String.join(", ", team.getMembers()) : "null";
        }

        // Placeholder %xteams_teams%
        if (params.equals("teams")) {
            return String.join(", ", plugin.getTeamManager().listTeams());
        }

        // Placeholder %xteams_teamexists_<team>%
        if (params.startsWith("teamexists_")) {
            String teamName = params.replace("teamexists_", "");
            return plugin.getTeamManager().teamExists(teamName) ? "true" : "false";
        }

        // Placeholder %xteams_teampriority_<team>%
        if (params.startsWith("teampriority_")) {
            String teamName = params.replace("teampriority_", "");
            Team team = plugin.getTeamManager().getTeam(teamName);
            return team != null ? String.valueOf(team.getPriority()) : "null";
        }

        return null; // Si el placeholder no coincide con ninguno de los anteriores
    }
    private String getTeamWithHighestPriority(OfflinePlayer player, boolean returnName) {
        Team highestPriorityTeam = null;
        int highestPriority = Integer.MIN_VALUE;

        // Obtener todos los nombres de equipos
        for (String teamName : plugin.getTeamManager().listTeams()) {
            Team team = plugin.getTeamManager().getTeam(teamName);

            if (team != null && team.hasMember(player)) {
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
