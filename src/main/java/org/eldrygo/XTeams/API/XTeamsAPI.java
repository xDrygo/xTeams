package org.eldrygo.XTeams.API;

import org.eldrygo.XTeams.Models.Team;
import org.eldrygo.XTeams.XTeams;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.Set;

public class XTeamsAPI {

    public static void setPlugin(XTeams plugin) {
        XTeamsAPI.plugin = plugin;
    }

    private static XTeams plugin;

    public XTeamsAPI(XTeams plugin) {
        XTeamsAPI.plugin = plugin;
    }

    // Delegar al TeamManager para obtener el equipo de un jugador
    public static String getPlayerTeamName(OfflinePlayer player) {
        return plugin.getTeamManager().getPlayerTeamName(player);
    }
    public static boolean setDisplayName(String teamName, String newDisplayName) {
        return plugin.getTeamManager().setTeamDisplayName(teamName, newDisplayName);
    }

    // Delegar al TeamManager para obtener el displayName de un jugador
    public static String getPlayerTeamDisplayName(OfflinePlayer player) {
        return plugin.getTeamManager().getPlayerTeamDisplayName(player);
    }
    public static String getTeamDisplayName(String teamName) {
        return plugin.getTeamManager().getTeamDisplayName(teamName);
    }

    // Delegar al TeamManager para obtener todos los equipos de un jugador
    public static List<Team> getPlayerTeams(OfflinePlayer player) {
        return plugin.getTeamManager().getPlayerTeams(player);
    }

    // Delegar al TeamManager para verificar si un jugador está en un equipo
    public static boolean isPlayerInTeam(OfflinePlayer player, String teamName) {
        return plugin.getTeamManager().hasPlayerInTeam(player, teamName);
    }

    // Delegar al TeamManager para obtener todos los miembros de un equipo
    public static Set<String> getTeamMembers(String teamName) {
        return plugin.getTeamManager().getTeamMembers(teamName);
    }

    // Delegar al TeamManager para listar los nombres de todos los equipos
    public static List<String> listTeams() {
        return plugin.getTeamManager().listTeams();
    }

    // Delegar al TeamManager para obtener la información de un equipo
    public static Team getTeamByName(String teamName) {
        return plugin.getTeamManager().getTeam(teamName);
    }

    // Delegar al TeamManager para crear un equipo
    public static void createTeam(String teamName, String displayName, int priority, Set<String> members) {
        plugin.getTeamManager().createTeam(teamName, displayName, priority, members);
    }

    // Delegar al TeamManager para eliminar un equipo
    public static void deleteTeam(String teamName) {
        plugin.getTeamManager().deleteTeam(teamName);
    }

    // Delegar al TeamManager para unirse a un equipo
    public static void joinTeam(OfflinePlayer player, String teamName) {
        plugin.getTeamManager().joinTeam(player, teamName);
    }

    // Delegar al TeamManager para abandonar un equipo
    public static void leaveTeam(OfflinePlayer player, String teamName) {
        plugin.getTeamManager().leaveTeam(player, teamName);
    }

    // Delegar al TeamManager para eliminar todos los equipos
    public static void deleteAllTeams() {
        plugin.getTeamManager().deleteAllTeams();
    }
}
