package org.eldrygo.Extensions;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.eldrygo.XTeams;
import org.eldrygo.Models.Team;

public class XTeamsExpansion extends PlaceholderExpansion {

    private final XTeams plugin;

    public XTeamsExpansion(XTeams plugin) { this.plugin = plugin; }

    @Override
    public boolean canRegister() { return true; }

    @Override
    public boolean persist() { return true; }

    @Override
    public String getIdentifier() { return "xteams"; }

    @Override
    public String getAuthor() { return "Drygo"; }

    @Override
    public String getVersion() { return plugin.getDescription().getVersion(); }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (player == null) {
            return "";
        }

        // Mensaje de depuración
        plugin.getLogger().info("Placeholder requested: " + params);

        // Verifica si el jugador pertenece a un equipo y devuelve el nombre del equipo
        if (params.equals("player_teamname")) {
            Team team = plugin.getTeamManager().getPlayerTeam(player);
            return team != null ? team.getName() : "No Team";
        }

        // Verifica si el jugador está en el equipo especificado (por nombre del equipo)
        if (params.startsWith("player_isinteam_")) {
            String teamName = params.replace("player_isinteam_", "");
            return String.valueOf(plugin.getTeamManager().isInTeam(player, teamName));
        }

        // Verifica si el equipo especificado tiene a un jugador específico
        if (params.startsWith("team_hasplayer_")) {
            String playerName = params.replace("team_hasplayer_", "");
            for (Team team : plugin.getTeamManager().getAllTeams()) {
                if (team.getMembers().contains(playerName)) {
                    return "true";
                }
            }
            return "false";
        }

        // Devuelve el número de miembros de un equipo especificado
        if (params.startsWith("team_numbermembers_")) {
            String teamName = params.replace("team_numbermembers_", "");
            Team team = plugin.getTeamManager().getTeam(teamName);
            if (team != null) {
                return String.valueOf(team.getMembers().size());
            }
        }

        // Agrega más placeholders aquí si es necesario

        return null; // Si no se encuentra un placeholder válido
    }
}
