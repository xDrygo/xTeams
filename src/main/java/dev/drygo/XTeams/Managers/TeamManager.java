package dev.drygo.XTeams.Managers;

import org.bukkit.Bukkit;
import dev.drygo.XTeams.API.Events.TeamCreateEvent;
import dev.drygo.XTeams.API.Events.TeamDeleteEvent;
import dev.drygo.XTeams.API.Events.TeamJoinEvent;
import dev.drygo.XTeams.API.Events.TeamLeaveEvent;
import dev.drygo.XTeams.Models.Team;

import java.util.*;

public class TeamManager {
    private static final Map<String, Team> teams = new HashMap<>();

    public static void addTeam(Team team) {
        teams.put(team.getName(), team);
        ConfigManager.saveTeamsToConfig();
    }

    public static Team getTeam(String teamName) {
        return teams.get(teamName);
    }

    public static void deleteTeam(Team team) {
        if (team != null) {
            teams.remove(team.getName());
            Bukkit.getPluginManager().callEvent(new TeamDeleteEvent(team.getName()));
            ConfigManager.saveTeamsToConfig();
        }
    }

    public static Set<Team> getAllTeams() {
        return new HashSet<>(teams.values());
    }

    public static boolean teamExists(String teamName) {
        return teams.containsKey(teamName);
    }

    public static Team getPlayerTeam(String nickname) {
        List<Team> playerTeams = getPlayerTeams(nickname);
        Team highest = null;
        for (Team team : playerTeams) {
            if (highest == null || team.getPriority() > highest.getPriority()) {
                highest = team;
            }
        }
        return highest;
    }

    public static Set<String> getTeamMembers(Team team) {
        return team != null ? team.getMembers() : new HashSet<>();
    }

    public static Set<String> getAllPlayersInTeams() {
        Set<String> allPlayers = new HashSet<>();
        for (Team team : getAllTeams()) {
            allPlayers.addAll(getTeamMembers(team));
        }
        return allPlayers;
    }

    public static void createTeam(String name, String displayName, int priority, Set<String> members) {
        if (!teamExists(name)) {
            Team team = new Team(name, displayName, priority, members);
            addTeam(team);
            Bukkit.getPluginManager().callEvent(new TeamCreateEvent(team));
        }
    }

    public static void deleteAllTeams() {
        teams.clear();
    }

    public static List<String> listTeams() {
        List<String> teamNames = new ArrayList<>();
        for (Team team : teams.values()) {
            teamNames.add(team.getName());
        }
        return teamNames;
    }

    public static Map<String, Object> getTeamInfo(Team team) {
        Map<String, Object> teamInfo = new HashMap<>();
        if (team != null) {
            teamInfo.put("name", team.getName());
            teamInfo.put("displayName", team.getDisplayName());
            teamInfo.put("members", team.getMembers());
            teamInfo.put("priority", team.getPriority());
        }
        return teamInfo;
    }

    public static void joinTeam(String nickname, Team team) {
        if (team != null && !team.getMembers().contains(nickname)) {
            team.addMember(nickname);
            ConfigManager.saveTeamsToConfig();
            Bukkit.getPluginManager().callEvent(new TeamJoinEvent(Bukkit.getOfflinePlayer(nickname), team.getName()));
        }
    }

    public static void joinAllTeams(String nickname) {
        for (Team team : teams.values()) {
            if (!team.getMembers().contains(nickname)) {
                team.addMember(nickname);
                ConfigManager.saveTeamsToConfig();
                Bukkit.getPluginManager().callEvent(new TeamJoinEvent(Bukkit.getOfflinePlayer(nickname), team.getName()));
            }
        }
    }

    public static void leaveTeam(String nickname, Team team) {
        if (team != null && team.getMembers().contains(nickname)) {
            team.removeMember(nickname);
            ConfigManager.saveTeamsToConfig();
            Bukkit.getPluginManager().callEvent(new TeamLeaveEvent(Bukkit.getOfflinePlayer(nickname), team.getName()));
        }
    }

    public static void leaveAllTeams(String nickname) {
        for (Team team : teams.values()) {
            if (team.getMembers().contains(nickname)) {
                team.removeMember(nickname);
                ConfigManager.saveTeamsToConfig();
                Bukkit.getPluginManager().callEvent(new TeamLeaveEvent(Bukkit.getOfflinePlayer(nickname), team.getName()));
            }
        }
    }

    public static boolean isInTeam(String nickname, Team team) {
        return team != null && team.getMembers().contains(nickname);
    }

    public static boolean isInAnyTeam(String nickname) {
        for (Team team : teams.values()) {
            if (team.getMembers().contains(nickname)) {
                return true;
            }
        }
        return false;
    }

    public static List<Team> getPlayerTeams(String nickname) {
        List<Team> result = new ArrayList<>();
        for (Team team : teams.values()) {
            if (team.getMembers().contains(nickname)) {
                result.add(team);
            }
        }
        return result;
    }

    public static Team getTeamByName(String name) {
        return teams.get(name);
    }

    public static void loadTeam(Team team) {
        teams.put(team.getName(), team);
    }
}
