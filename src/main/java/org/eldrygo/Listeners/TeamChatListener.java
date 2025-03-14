package org.eldrygo.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.eldrygo.Managers.TeamManager;
import org.eldrygo.Models.Team;
import org.eldrygo.Utils.ChatUtils;
import org.eldrygo.XTeams;

public class TeamChatListener {

    private final XTeams plugin;
    private ChatUtils chatUtils;

    public TeamChatListener(XTeams plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (message.startsWith("@")) {
            String teamName = plugin.getTeamManager().getPlayerTeam(player);
            if (teamName == null) {
                player.sendMessage(chatUtils.getMessage("teams.private_chat.team_not_found"));
                event.setCancelled(true);
                return;
            }

            String teamMessage = message.substring(1).trim();

            // Obtener el equipo y luego iterar sobre sus miembros
            Team team = plugin.getTeamManager().getTeamMembers(teamName);
            if (team != null) {
                for (String memberName : team.getMembers()) {
                    Player member = plugin.getServer().getPlayer(memberName);
                    if (member != null && member.isOnline()) {
                        member.sendMessage(chatUtils.getMessage("teams.private_chat.chat")
                                .replace("%sender%", player.getName())
                                .replace("%message%", teamMessage));
                    }
                }
            }
            event.setCancelled(true);
        }
    }
}