package dev.drygo.XTeams.Hooks.AutoTeam.Managers;

import dev.drygo.XTeams.XTeams;

public class AutoTeamManager {
    private final XTeams plugin;

    private String autoTeamTeam;
    private boolean opByPass;

    public AutoTeamManager(XTeams plugin) {
        this.plugin = plugin;
    }

    public void load() {
        autoTeamTeam = plugin.getConfig().getString("hooks.auto_team.team");
        opByPass = plugin.getConfig().getBoolean("hooks.auto_team.op_bypass", false);
    }

    public String getAutoTeamTeam() {
        return autoTeamTeam;
    }
    public boolean enabledOpByPass() {
        return opByPass;
    }
}
