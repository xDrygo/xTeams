package org.eldrygo.XTeams.Managers;

import org.bukkit.Bukkit;
import org.eldrygo.XTeams.API.Events.TeamCreateEvent;
import org.eldrygo.XTeams.API.Events.TeamDeleteEvent;
import org.eldrygo.XTeams.API.Events.TeamJoinEvent;
import org.eldrygo.XTeams.API.Events.TeamLeaveEvent;
import org.eldrygo.XTeams.Models.Team;
import org.eldrygo.XTeams.XTeams;

import java.util.*;

public class TeamManager {

    private final XTeams plugin;
    private final Map<String, Team> teams;
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

    public void deleteTeam(Team team) {
        if (team != null) {
            teams.remove(team.getName());
            Bukkit.getPluginManager().callEvent(new TeamDeleteEvent(team.getName()));
        }
    }

    public Set<Team> getAllTeams() {
        return new HashSet<>(teams.values());
    }

    public boolean teamExists(String teamName) {
        return teams.containsKey(teamName);
    }

    public Team getPlayerTeam(String nickname) {
        for (Team team : teams.values()) {
            if (team.getMembers().contains(nickname)) {
                return team;
            }
        }
        return null;
    }

    public Set<String> getTeamMembers(Team team) {
        return team != null ? team.getMembers() : new HashSet<>();
    }

    public void createTeam(String name, String displayName, int priority, Set<String> members) {
        if (!teamExists(name)) {
            Team team = new Team(name, displayName, priority, members);
            addTeam(team);
            Bukkit.getPluginManager().callEvent(new TeamCreateEvent(team));
        }
    }

    public void deleteAllTeams() {
        teams.clear();
    }

    public List<String> listTeams() {
        List<String> teamNames = new ArrayList<>();
        for (Team team : teams.values()) {
            teamNames.add(team.getName());
        }
        return teamNames;
    }

    public Map<String, Object> getTeamInfo(Team team) {
        Map<String, Object> teamInfo = new HashMap<>();
        if (team != null) {
            teamInfo.put("name", team.getName());
            teamInfo.put("displayName", team.getDisplayName());
            teamInfo.put("members", team.getMembers());
            teamInfo.put("priority", team.getPriority());
        }
        return teamInfo;
    }

    public void joinTeam(String nickname, Team team) {
        if (team != null && !team.getMembers().contains(nickname)) {
            team.addMember(nickname);
            Bukkit.getPluginManager().callEvent(new TeamJoinEvent(Bukkit.getOfflinePlayer(nickname), team.getName()));
        }
    }

    public void joinAllTeams(String nickname) {
        for (Team team : teams.values()) {
            if (!team.getMembers().contains(nickname)) {
                team.addMember(nickname);
                Bukkit.getPluginManager().callEvent(new TeamJoinEvent(Bukkit.getOfflinePlayer(nickname), team.getName()));
            }
        }
    }

    public void leaveTeam(String nickname, Team team) {
        if (team != null && team.getMembers().contains(nickname)) {
            team.removeMember(nickname);
            Bukkit.getPluginManager().callEvent(new TeamLeaveEvent(Bukkit.getOfflinePlayer(nickname), team.getName()));
        }
    }

    public void leaveAllTeams(String nickname) {
        for (Team team : teams.values()) {
            if (team.getMembers().contains(nickname)) {
                team.removeMember(nickname);
                Bukkit.getPluginManager().callEvent(new TeamLeaveEvent(Bukkit.getOfflinePlayer(nickname), team.getName()));
            }
        }
    }

    public boolean isInTeam(String nickname, Team team) {
        return team != null && team.getMembers().contains(nickname);
    }

    public boolean isInAnyTeam(String nickname) {
        for (Team team : teams.values()) {
            if (team.getMembers().contains(nickname)) {
                return true;
            }
        }
        return false;
    }

    public String getPlayerTeamName(String nickname) {
        Team highestPriorityTeam = getHighestPriorityTeam(nickname);
        return (highestPriorityTeam != null) ? highestPriorityTeam.getName() : "No Team";
    }

    public String getPlayerTeamDisplayName(String nickname) {
        Team highestPriorityTeam = getHighestPriorityTeam(nickname);
        return (highestPriorityTeam != null) ? highestPriorityTeam.getDisplayName() : "No Team";
    }

    private Team getHighestPriorityTeam(String nickname) {
        List<Team> playerTeams = getPlayerTeams(nickname);
        Team highest = null;
        for (Team team : playerTeams) {
            if (highest == null || team.getPriority() > highest.getPriority()) {
                highest = team;
            }
        }
        return highest;
    }

    public List<Team> getPlayerTeams(String nickname) {
        List<Team> result = new ArrayList<>();
        for (Team team : teams.values()) {
            if (team.getMembers().contains(nickname)) {
                result.add(team);
            }
        }
        return result;
    }

    public int getTeamNumberOfMembers(Team team) {
        return (team != null) ? team.getMembers().size() : 0;
    }

    public boolean setTeamDisplayName(Team team, String newDisplayName) {
        if (team == null) return false;
        team.setDisplayName(newDisplayName);
        configManager.saveTeamsToConfig();
        return true;
    }

    public String getTeamDisplayName(Team team) {
        return team != null ? team.getDisplayName() : "Unknown";
    }

    public Team getTeamByName(String name) {
        return teams.get(name);
    }
}
