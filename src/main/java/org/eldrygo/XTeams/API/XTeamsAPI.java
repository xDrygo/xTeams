package org.eldrygo.XTeams.API;

import org.eldrygo.XTeams.Managers.TeamManager;
import org.eldrygo.XTeams.Models.Team;

import java.util.*;

public class XTeamsAPI {

    private static TeamManager manager;

    public static void init(TeamManager teamManager) {
        manager = teamManager;
    }

    public static Team getTeam(String name) {
        return manager.getTeamByName(name);
    }

    public static boolean teamExists(String name) {
        return manager.teamExists(name);
    }

    public static Set<Team> getAllTeams() {
        return manager.getAllTeams();
    }

    public static void createTeam(String name, String displayName, int priority, Set<String> members) {
        manager.createTeam(name, displayName, priority, members);
    }

    public static void deleteTeam(Team team) {
        manager.deleteTeam(team);
    }

    public static Map<String, Object> getTeamInfo(Team team) {
        return manager.getTeamInfo(team);
    }

    public static void joinTeam(String player, Team team) {
        manager.joinTeam(player, team);
    }

    public static void leaveTeam(String player, Team team) {
        manager.leaveTeam(player, team);
    }

    public static void joinAllTeams(String player) {
        manager.joinAllTeams(player);
    }

    public static void leaveAllTeams(String player) {
        manager.leaveAllTeams(player);
    }

    public static boolean isInTeam(String player, Team team) {
        return manager.isInTeam(player, team);
    }

    public static boolean isInAnyTeam(String player) {
        return manager.isInAnyTeam(player);
    }

    public static List<Team> getPlayerTeams(String player) {
        return manager.getPlayerTeams(player);
    }

    public static Team getPlayerTeam(String player) {
        return manager.getPlayerTeam(player);
    }

    public static String getPlayerTeamName(String player) {
        return manager.getPlayerTeamName(player);
    }

    public static String getPlayerTeamDisplayName(String player) {
        return manager.getPlayerTeamDisplayName(player);
    }

    public static boolean setTeamDisplayName(Team team, String displayName) {
        return manager.setTeamDisplayName(team, displayName);
    }

    public static String getTeamDisplayName(Team team) {
        return manager.getTeamDisplayName(team);
    }

    public static int getTeamMemberCount(Team team) {
        return manager.getTeamNumberOfMembers(team);
    }

    public static Set<String> getTeamMembers(Team team) {
        return manager.getTeamMembers(team);
    }

    public static List<String> listTeamNames() {
        return manager.listTeams();
    }
}
