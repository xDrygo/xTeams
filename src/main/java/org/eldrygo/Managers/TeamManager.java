package org.eldrygo.Managers;

import org.bukkit.entity.Player;
import org.eldrygo.Models.Team;
import org.eldrygo.XTeams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TeamManager {
    private final XTeams plugin;

    public TeamManager(XTeams plugin) {
        this.plugin = plugin;
    }

    // Verifica si dos jugadores están en el mismo equipo
    public boolean isSameTeam(Player player1, Player player2) {
        return plugin.team.teams.values().stream()
                .anyMatch(team -> team.getMembers().contains(player1.getName())
                        && team.getMembers().contains(player2.getName()));
    }

    // Obtiene el equipo correspondiente a un nombre
    public Team getTeamMembers(String teamName) {
        return plugin.team.teams.getOrDefault(teamName.toLowerCase(), null);
    }

    // Obtiene el equipo de un jugador
    public String getPlayerTeam(Player player) {
        return plugin.team.teams.entrySet().stream()
                .filter(entry -> entry.getValue().getMembers().contains(player.getName()))
                .map(Map.Entry::getKey)
                .findFirst().orElse(null);
    }

    // Obtiene todos los equipos
    public Set<String> getAllTeams() {
        return plugin.team.teams.keySet();
    }

    // Verifica si el jugador pertenece a algún equipo
    public boolean isInTeam(Player player) {
        return getPlayerTeam(player) != null;
    }

    // Crea un nuevo equipo
    public void createTeam(String name) {
        plugin.team.teams.put(name.toLowerCase(), new Team(name, name)); // DisplayName = Nombre por defecto
    }

    // Elimina un equipo específico
    public void deleteTeam(String name) {
        plugin.team.teams.remove(name.toLowerCase());
    }

    // Elimina todos los equipos
    public void deleteAllTeams() {
        plugin.team.teams.clear();
    }

    // Lista todos los equipos existentes
    public String listTeams() {
        return String.join(", ", plugin.team.teams.keySet());
    }

    // Añade un jugador a un equipo específico
    public void joinTeam(Player player, String teamName) {
        Team team = plugin.team.teams.get(teamName.toLowerCase());
        if (team != null) {
            team.addMember(player.getName());
        }
    }

    // Añade un jugador a todos los equipos
    public void joinAllTeams(Player player) {
        plugin.team.teams.values().forEach(team -> team.addMember(player.getName()));
    }

    // Elimina a un jugador de un equipo específico
    public void leaveTeam(Player player, String teamName) {
        Team team = plugin.team.teams.get(teamName.toLowerCase());
        if (team != null) {
            team.removeMember(player.getName());
        }
    }

    // Elimina a un jugador de todos los equipos
    public void leaveAllTeams(Player player) {
        plugin.team.teams.values().forEach(team -> team.removeMember(player.getName()));
    }

    // Obtiene la información de un equipo
    public Map<String, Object> getTeamInfo(String[] teamName) {
        Team team = plugin.team.teams.get(teamName);
        if (team == null) {
            return null; // Equipo no encontrado
        }

        Map<String, Object> info = new HashMap<>();
        info.put("name", team.getName());
        info.put("displayName", team.getDisplayName());
        info.put("members", new ArrayList<>(team.getMembers()));

        return info;
    }
}