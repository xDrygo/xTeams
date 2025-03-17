package org.eldrygo.API;

import org.eldrygo.Models.Team;
import org.eldrygo.XTeams;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.Set;

public class XTeamsAPI {

    private final XTeams plugin;

    public XTeamsAPI(XTeams plugin) {
        this.plugin = plugin;
    }

    // Delegar al TeamManager para obtener el equipo de un jugador
    public String getPlayerTeamName(OfflinePlayer player) {
        return plugin.getTeamManager().getPlayerTeamName(player);
    }
    public boolean setDisplayName(String teamName, String newDisplayName) {
        return plugin.getTeamManager().setTeamDisplayName(teamName, newDisplayName);
    }

    // Delegar al TeamManager para obtener el displayName de un jugador
    public String getPlayerTeamDisplayName(OfflinePlayer player) {
        return plugin.getTeamManager().getPlayerTeamDisplayName(player);
    }

    // Delegar al TeamManager para obtener todos los equipos de un jugador
    public List<Team> getPlayerTeams(OfflinePlayer player) {
        return plugin.getTeamManager().getPlayerTeams(player);
    }

    // Delegar al TeamManager para verificar si un jugador está en un equipo
    public boolean isPlayerInTeam(OfflinePlayer player, String teamName) {
        return plugin.getTeamManager().hasPlayerInTeam(player, teamName);
    }

    // Delegar al TeamManager para obtener todos los miembros de un equipo
    public Set<String> getTeamMembers(String teamName) {
        return plugin.getTeamManager().getTeamMembers(teamName);
    }

    // Delegar al TeamManager para listar los nombres de todos los equipos
    public List<String> listTeams() {
        return plugin.getTeamManager().listTeams();
    }

    // Delegar al TeamManager para obtener la información de un equipo
    public Team getTeamByName(String teamName) {
        return plugin.getTeamManager().getTeam(teamName);
    }

    // Delegar al TeamManager para crear un equipo
    public void createTeam(String teamName, String displayName, int priority, Set<String> members) {
        plugin.getTeamManager().createTeam(teamName, displayName, priority, members);
    }

    // Delegar al TeamManager para eliminar un equipo
    public void deleteTeam(String teamName) {
        plugin.getTeamManager().deleteTeam(teamName);
    }

    // Delegar al TeamManager para unirse a un equipo
    public void joinTeam(OfflinePlayer player, String teamName) {
        plugin.getTeamManager().joinTeam(player, teamName);
    }

    // Delegar al TeamManager para abandonar un equipo
    public void leaveTeam(OfflinePlayer player, String teamName) {
        plugin.getTeamManager().leaveTeam(player, teamName);
    }

    // Delegar al TeamManager para eliminar todos los equipos
    public void deleteAllTeams() {
        plugin.getTeamManager().deleteAllTeams();
    }
}
