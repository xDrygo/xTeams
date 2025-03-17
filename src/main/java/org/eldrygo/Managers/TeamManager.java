package org.eldrygo.Managers;

import org.eldrygo.Models.Team;
import org.eldrygo.XTeams;
import org.bukkit.OfflinePlayer;

import java.util.*;

public class TeamManager {

    private final XTeams plugin;
    private final Map<String, Team> teams; // Utilizamos un mapa para almacenar equipos por su nombre.
    private final ConfigManager configManager;

    public TeamManager(XTeams plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.teams = new HashMap<>();
    }

    public void addTeam(Team team) {
        teams.put(team.getName(), team);
    }

    public Team getTeam(String teamName) {
        return teams.get(teamName);
    }

    public void deleteTeam(String teamName) {
        teams.remove(teamName);
    }

    public Set<Team> getAllTeams() {
        return new HashSet<>(teams.values());
    }

    public boolean teamExists(String teamName) {
        return teams.containsKey(teamName);
    }

    public Team getPlayerTeam(OfflinePlayer player) {
        // Verifica si el jugador pertenece a algún equipo
        for (Team team : teams.values()) {
            if (team.getMembers().contains(player.getName())) {
                return team;
            }
        }
        return null; // El jugador no pertenece a ningún equipo
    }

    public Set<String> getTeamMembers(String teamName) {
        Team team = teams.get(teamName);
        if (team != null) {
            return team.getMembers();
        }
        return new HashSet<>();
    }

    public void createTeam(String teamName, String displayName, int priority, Set<String> members) {
        if (!teamExists(teamName)) {
            Team team = new Team(teamName, displayName, priority, members);
            addTeam(team);
        }
    }

    public void deleteAllTeams() {
        teams.clear();
    }

    public List<String> listTeams() {
        plugin.getLogger().info("Listing all teams:");
        List<String> teamNames = new ArrayList<>();
        for (Team team : teams.values()) {
            plugin.getLogger().info("Team Name: " + team.getName() + " | Display Name: " + team.getDisplayName() + " | Members: " + team.getMembers().size());
            teamNames.add(team.getName()); // Add team names to the list
        }
        return teamNames; // Returning the list of team names
    }

    public Map<String, Object> getTeamInfo(String teamName) {
        Team team = getTeam(teamName);
        Map<String, Object> teamInfo = new HashMap<>();
        if (team != null) {
            teamInfo.put("name", team.getName());
            teamInfo.put("displayName", team.getDisplayName());
            teamInfo.put("members", team.getMembers());
            teamInfo.put("priority", team.getPriority());
            plugin.getLogger().info("Team Name: " + team.getName() + " | Display Name: " + team.getDisplayName() + " | Priority: " + team.getPriority() + " | Members: " + team.getMembers ());
        } else {
            plugin.getLogger().warning("Team " + teamName + " does not exist.");
        }
        return teamInfo; // Returning the info map
    }

    public void joinTeam(OfflinePlayer player, String teamName) {
        Team team = getTeam(teamName);
        if (team != null) {
            team.addMember(player.getName());
            plugin.getLogger().info(player.getName() + " has joined team " + teamName);
        } else {
            plugin.getLogger().warning("Team " + teamName + " does not exist.");
        }
    }

    public void joinAllTeams(OfflinePlayer player) {
        for (Team team : teams.values()) {
            team.addMember(player.getName());
        }
        plugin.getLogger().info(player.getName() + " has joined all teams.");
    }

    public void leaveTeam(OfflinePlayer player, String teamName) {
        Team team = getTeam(teamName);
        if (team != null && team.getMembers().contains(player.getName())) {
            team.removeMember(player.getName());
            plugin.getLogger().info(player.getName() + " has left team " + teamName);
        } else {
            plugin.getLogger().warning(player.getName() + " is not a member of team " + teamName);
        }
    }

    public void leaveAllTeams(OfflinePlayer player) {
        for (Team team : teams.values()) {
            if (team.getMembers().contains(player.getName())) {
                team.removeMember(player.getName());
            }
        }
        plugin.getLogger().info(player.getName() + " has left all teams.");
    }

    public boolean isInTeam(OfflinePlayer player, String teamName) {
        Team team = getTeam(teamName);
        return team != null && team.hasMember(player);
    }

    // Método para comprobar si un jugador está en algún equipo
    public boolean isInAnyTeam(OfflinePlayer player) {
        for (Team team : teams.values()) {
            if (team.hasMember(player)) {
                return true;
            }
        }
        return false;
    }

    public Team getTeamByName(String teamName) {
        return teams.get(teamName); // Suponiendo que teams es un mapa que contiene los equipos
    }

    // Métodos para los placeholders

    // Obtiene el nombre del equipo con mayor prioridad al que pertenece el jugador
    public String getPlayerTeamName(OfflinePlayer player) {
        Team highestPriorityTeam = getHighestPriorityTeam(player); // Obtener el equipo con mayor prioridad
        return (highestPriorityTeam != null) ? highestPriorityTeam.getName() : "No Team";
    }

    // Obtiene el displayName del equipo con mayor prioridad al que pertenece el jugador
    public String getPlayerTeamDisplayName(OfflinePlayer player) {
        Team highestPriorityTeam = getHighestPriorityTeam(player); // Obtener el equipo con mayor prioridad
        return (highestPriorityTeam != null) ? highestPriorityTeam.getDisplayName() : "No Team";
    }

    // Obtiene el equipo con mayor prioridad al que pertenece el jugador
    private Team getHighestPriorityTeam(OfflinePlayer player) {
        List<Team> playerTeams = getPlayerTeams(player); // Obtener todos los equipos del jugador
        Team highestPriorityTeam = null;

        // Buscar el equipo con mayor prioridad
        for (Team team : playerTeams) {
            if (highestPriorityTeam == null || team.getPriority() > highestPriorityTeam.getPriority()) {
                highestPriorityTeam = team;
            }
        }

        return highestPriorityTeam;
    }
    public List<Team> getPlayerTeams(OfflinePlayer player) {
        List<Team> playerTeams = new ArrayList<>();
        for (Team team : teams.values()) {
            if (team.hasMember(player)) {
                playerTeams.add(team);
            }
        }
        return playerTeams;
    }
    private int getTeamPriority(Team team) {
        return team.getPriority();
    }
    public boolean hasPlayerInTeam(OfflinePlayer player, String teamName) {
        Team team = getTeam(teamName);
        if (team != null) {
            return team.hasMember(player);
        }
        return false; // Si el equipo no existe, retornamos false
    }

    public int getTeamNumberOfMembers(String teamName) {
        Team team = getTeam(teamName);
        return (team != null) ? team.getMembers().size() : 0;
    }

    public boolean isPlayerInAnyTeam(OfflinePlayer player) {
        return getPlayerTeam(player) != null;
    }
    
    public boolean setTeamDisplayName(String teamName, String newDisplayName) {
        Team team = getTeamByName(teamName);
        if (team == null) {
            return false;  // No se encontró el equipo
        }
        team.setDisplayName(newDisplayName);  // Cambiar el display name
        configManager.saveTeamsToConfig();  // Guardar los cambios en la configuración
        return true;
    }
}
