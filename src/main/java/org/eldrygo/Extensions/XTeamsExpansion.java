package org.eldrygo.Extensions;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.eldrygo.XTeams;
import org.eldrygo.Models.Team;
import org.jetbrains.annotations.NotNull;

public class XTeamsExpansion extends PlaceholderExpansion {

    private final XTeams plugin;

    public XTeamsExpansion(XTeams plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getIdentifier() {
        return "xteams";
    }

    @Override
    public String getAuthor() {
        return "Drygo";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
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

        if (params.startsWith("hasplayer_")) {
            String[] parts = params.replace("hasplayer_", "").split("_");
            if (parts.length == 2) {
                String targetPlayerName = parts[0];
                String teamName = parts[1];
                Team team = plugin.getTeamManager().getTeam(teamName);
                OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(targetPlayerName); // Convertimos a OfflinePlayer
                return (team != null && team.hasMember(targetPlayer)) ? "true" : "false"; // Ahora pasamos un OfflinePlayer
            }
        }

        if (params.startsWith("playercount_")) {
            String teamName = params.replace("playercount_", "");
            Team team = plugin.getTeamManager().getTeam(teamName);
            return team != null ? String.valueOf(team.getMembers().size()) : "0";
        }

        if (params.startsWith("teammembers_")) {
            String teamName = params.replace("teammembers_", "");
            Team team = plugin.getTeamManager().getTeam(teamName);
            return team != null ? String.join(", ", team.getMembers()) : "No Members";
        }

        if (params.equals("teams")) {
            return String.join(", ", plugin.getTeamManager().listTeams());
        }

        if (params.startsWith("teamexists_")) {
            String teamName = params.replace("teamexists_", "");
            return plugin.getTeamManager().teamExists(teamName) ? "true" : "false";
        }

        if (params.startsWith("teampriority_")) {
            String teamName = params.replace("teampriority_", "");
            Team team = plugin.getTeamManager().getTeam(teamName);
            return team != null ? String.valueOf(team.getPriority()) : "No Priority";
        }

        return null; // Si el placeholder no coincide con ninguno de los anteriores
    }
    private String getTeamWithHighestPriority(OfflinePlayer player, boolean returnName) {
        Team highestPriorityTeam = null;
        int highestPriority = Integer.MIN_VALUE;

        // Obtener todos los nombres de equipos
        for (String teamName : plugin.getTeamManager().listTeams()) {
            Team team = plugin.getTeamManager().getTeam(teamName); // Obtener el objeto Team por nombre

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

        return "No Team"; // Si no hay equipo encontrado
    }
}
