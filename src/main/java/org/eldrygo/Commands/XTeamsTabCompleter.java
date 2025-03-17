package org.eldrygo.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.eldrygo.Models.Team;
import org.eldrygo.Utils.ChatUtils;
import org.eldrygo.XTeams;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class XTeamsTabCompleter implements TabCompleter {
    private final ChatUtils chatUtils;
    private XTeams plugin;

    public XTeamsTabCompleter(XTeams plugin) {
        this.plugin = plugin;
        this.chatUtils = plugin.getChatUtils();
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        String teamNameMessage = chatUtils.getMessage("tabcomplete.create.team", (Player) sender);
        String priorityMessage = chatUtils.getMessage("tabcomplete.create.priority", (Player) sender);
        String displayNameMessage = chatUtils.getMessage("tabcomplete.setdisplay.display_name", (Player) sender);
        // Primero comprobamos si el jugador tiene el permiso para el subcomando
        if (args.length == 0) {
            return Collections.emptyList();  // No hay nada que autocompletar si no hay argumentos
        }

        // Lista de subcomandos disponibles
        List<String> subCommands = new ArrayList<>();

        // Comprobamos si el jugador tiene permiso para los subcomandos
        if (sender.hasPermission("xteams.command.create")) subCommands.add("create");
        if (sender.hasPermission("xteams.command.delete")) subCommands.add("delete");
        if (sender.hasPermission("xteams.command.join")) subCommands.add("join");
        if (sender.hasPermission("xteams.command.leave")) subCommands.add("leave");
        if (sender.hasPermission("xteams.command.setdisplay")) subCommands.add("setdisplay");
        if (sender.hasPermission("xteams.command.info")) subCommands.add("info");
        if (sender.hasPermission("xteams.command.teaminfo")) subCommands.add("teaminfo");
        if (sender.hasPermission("xteams.command.playerinfo")) subCommands.add("playerinfo");
        if (sender.hasPermission("xteams.command.reload")) subCommands.add("reload");
        if (sender.hasPermission("xteams.command.list")) subCommands.add("list");
        if (sender.hasPermission("xteams.command.help")) subCommands.add("help");

        // Si hay subcomandos disponibles y solo tenemos un argumento, completamos el subcomando
        if (args.length == 1) {
            return getMatches(args[0], subCommands);  // Autocompleta el subcomando
        }

        // Ahora manejamos los subcomandos específicos
        switch (args[0].toLowerCase()) {
            case "create" -> {
                if (args.length == 2) {
                    return Collections.singletonList(teamNameMessage);
                }
                if (args.length == 3) {
                    return Collections.singletonList(priorityMessage);
                }
            }
            case "delete" -> {
                if (args.length == 2) {
                    // Sugerir la lista de equipos
                    List<String> teams = getTeamsList();
                    teams.add("*");  // Agregar '*' para eliminar todos los equipos
                    return getMatches(args[1], teams);
                }
            }
            case "setdisplay" -> {
                if (args.length == 2) {
                    // Sugerir equipos
                    List<String> teams = getTeamsList();
                    return getMatches(args[1], teams);
                } else if (args.length == 3) {
                    return Collections.singletonList("\"" + displayNameMessage + "\"");  // Al llegar al tercer argumento
                }
            }
            case "join" -> {
                if (args.length == 2) {
                    // Sugerir equipos para unirse
                    return getMatches(args[1], getTeamsList());
                }
                if (args.length == 3) {
                    // Sugerir jugadores para unirse
                    return getMatches(args[2], getPlayersList());
                }
            }
            case "leave" -> {
                if (args.length == 2) {
                    // Sugerir equipos a los cuales el jugador puede dejar
                    return getMatches(args[1], getTeamsList());
                }
                if (args.length == 3) {
                    // Sugerir jugadores para dejar un equipo
                    return getMatches(args[2], getPlayersList());
                }
            }
            case "info", "list", "help" -> {
                return Collections.emptyList();
            }
            case "teaminfo" -> {
                if (args.length == 2) {
                    // Mostrar información sobre equipos o jugadores
                    return getMatches(args[1], getTeamsList());
                }
            }
            case "playerinfo" -> {
                if (args.length == 2) {
                    return getMatches(args[1], getPlayersList());
                }
            }
            default -> {
            }
        }

        return Collections.emptyList();  // Si no hay coincidencias, retornamos vacío
    }

    private List<String> getMatches(String arg, List<String> options) {
        List<String> matches = new ArrayList<>();
        for (String option : options) {
            if (option.toLowerCase().startsWith(arg.toLowerCase())) {
                matches.add(option);
            }
        }
        return matches;
    }

    private List<String> getTeamsList() {
        List<String> teams = new ArrayList<>();

        // Acceder al gestor de equipos del plugin y obtener todos los equipos
        Set<Team> allTeams = plugin.getTeamManager().getAllTeams();

        // Verificar si hay equipos disponibles
        if (allTeams != null && !allTeams.isEmpty()) {
            // Iterar sobre todos los equipos y agregar sus nombres a la lista
            for (Team team : allTeams) {
                teams.add(team.getName());  // Añadir el nombre del equipo a la lista
            }
        }

        return teams;
    }

    private List<String> getPlayersList() {
        List<String> players = new ArrayList<>();
        // Obtener jugadores conectados en el servidor
        for (Player player : Bukkit.getOnlinePlayers()) {
            players.add(player.getName());
        }
        return players;
    }
}
