package dev.drygo.XTeams.Hooks.Listeners;

import dev.drygo.XTeams.API.Events.TeamJoinEvent;
import dev.drygo.XTeams.API.Events.TeamLeaveEvent;
import dev.drygo.XTeams.Models.Team;
import dev.drygo.XTeams.XTeams;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class TeamListener implements Listener {
    private final XTeams plugin;

    public TeamListener(XTeams plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!plugin.isEnabledAutoTeam()) return;
        if (plugin.getAutoTeamManager().enabledOpByPass() && player.isOp()) return;
        if (!plugin.getTeamManager().getPlayerTeams(player.getName()).isEmpty()) return;
        Team autoTeamTeam = plugin.getTeamManager().getTeamByName(plugin.getAutoTeamManager().getAutoTeamTeam());
        if (autoTeamTeam == null) return;
        plugin.getTeamManager().joinTeam(player.getName(), autoTeamTeam);
    }

    @EventHandler
    public void onTeamJoin(TeamJoinEvent event) {
        Player player = (Player) event.getPlayer();
        Team team = plugin.getTeamManager().getTeam(event.getTeamName());
        if (plugin.isEnabledLuckPermsHook()) {
            plugin.getLuckPermsGroupManager().applyGroup(player, team.getName());
        }
        if (plugin.isEnabledMinecraftTeamHook()) {
            plugin.getMinecraftTeamManager().applyGroup(player, team.getName());
        }
    }

    @EventHandler
    public void onTeamLeave(TeamLeaveEvent event) {
        Player player = (Player) event.getPlayer();
        Team team = plugin.getTeamManager().getTeam(event.getTeamName());
        if (plugin.isEnabledLuckPermsHook()) {
            plugin.getLuckPermsGroupManager().removeGroup(player, team.getName());
        }
        if (plugin.isEnabledMinecraftTeamHook()) {
            plugin.getMinecraftTeamManager().removeGroup(player, team.getName());
        }
    }
}
