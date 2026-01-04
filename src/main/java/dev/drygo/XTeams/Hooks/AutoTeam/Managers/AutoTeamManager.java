package dev.drygo.XTeams.Hooks.AutoTeam.Managers;

import dev.drygo.XTeams.XTeams;

public class AutoTeamManager {
    private static XTeams plugin;

    private static String autoTeamTeam;
    private static boolean opByPass;

    public static void init(XTeams plugin) {
        AutoTeamManager.plugin = plugin;
    }

    public static void load() {
        autoTeamTeam = plugin.getConfig().getString("hooks.auto_team.team");
        opByPass = plugin.getConfig().getBoolean("hooks.auto_team.op_bypass", false);
    }

    public static String getAutoTeamTeam() {
        return autoTeamTeam;
    }
    public static boolean enabledOpByPass() {
        return opByPass;
    }
}
