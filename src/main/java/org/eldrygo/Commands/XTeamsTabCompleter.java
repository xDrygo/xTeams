package org.eldrygo.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class XTeamsTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        // Primero comprobamos si el jugador tiene el permiso para el subcomando
        if (args.length == 0) {
            return Collections.emptyList();  // No hay nada que autocompletar si no hay argumentos
        }

        // Comprobamos si el jugador tiene permiso para el subcomando especificado
        String subCommand = args[0].toLowerCase();

        // Lista de subcomandos disponibles
        List<String> subCommands = new ArrayList<>();

        if (subCommand.equals("create") && sender.hasPermission("xteams.command.create")) {
            subCommands.add("create");
        } else if (subCommand.equals("delete") && sender.hasPermission("xteams.command.delete")) {
            subCommands.add("delete");
        } else if (subCommand.equals("join") && sender.hasPermission("xteams.command.join")) {
            subCommands.add("join");
        } else if (subCommand.equals("leave") && sender.hasPermission("xteams.command.leave")) {
            subCommands.add("leave");
        } else if (subCommand.equals("setdisplay") && sender.hasPermission("xteams.command.setdisplay")) {
            subCommands.add("setdisplay");
        } else if (subCommand.equals("info") && sender.hasPermission("xteams.command.info")) {
            subCommands.add("info");
        } else if (subCommand.equals("teaminfo") && sender.hasPermission("xteams.command.teaminfo")) {
            subCommands.add("teaminfo");
        } else if (subCommand.equals("playerinfo") && sender.hasPermission("xteams.command.playerinfo")) {
            subCommands.add("playerinfo");
        } else if (subCommand.equals("reload") && sender.hasPermission("xteams.command.reload")) {
            subCommands.add("reload");
        } else if (subCommand.equals("list") && sender.hasPermission("xteams.command.list")) {
            subCommands.add("list");
        } else if (subCommand.equals("help") && sender.hasPermission("xteams.command.help")) {
            subCommands.add("help");
        }

        // Si el subcomando no tiene permisos o no es válido
        if (subCommands.isEmpty()) {
            return Collections.emptyList();
        }

        // Si solo tenemos un argumento (el subcomando), devolvemos los subcomandos disponibles con permisos
        if (args.length == 1) {
            return subCommands;
        }

        // Aquí manejamos los argumentos según el subcomando
        switch (subCommand.toLowerCase()) {
            case "create":
                if (args.length == 2 && sender.hasPermission("xteams.command.create")) {
                    // Espacio para que el jugador escriba el nombre del equipo
                    return Collections.emptyList();  // No se autocompleta el nombre, solo se deja escribir
                }
                break;

            case "delete":
                if (args.length == 2 && sender.hasPermission("xteams.command.delete")) {
                    // Sugerir la lista de equipos disponibles y añadir '*' para eliminar todos los equipos
                    List<String> teams = getTeamsList();
                    teams.add("*");
                    return getMatches(args[1], teams);
                }
                break;
            case "setdisplay":
                if (args.length == 2 && sender.hasPermission("xteams.command.setdisplay")) {
                    // Sugerir la lista de equipos disponibles
                    List<String> teams = getTeamsList();
                    return getMatches(args[1], teams);  // Filtra las sugerencias según lo que el jugador ha escrito
                } else if (args.length == 3) {
                    // Al llegar al tercer argumento, sugerir que se escriba entre comillas
                    return Collections.singletonList("\"\"");
                }
                break;

            case "join":
                if (args.length == 2 && sender.hasPermission("xteams.command.join")) {
                    // Sugerir equipos para unirse
                    return getMatches(args[1], getTeamsList());
                }
                if (args.length == 3 && sender.hasPermission("xteams.command.join")) {
                    // Sugerir jugadores para unirse a un equipo
                    return getMatches(args[2], getPlayersList());
                }
                break;

            case "leave":
                if (args.length == 2 && sender.hasPermission("xteams.command.leave")) {
                    // Sugerir equipos a los cuales el jugador puede dejar
                    return getMatches(args[1], getTeamsList());
                }
                if (args.length == 3 && sender.hasPermission("xteams.command.leave")) {
                    // Sugerir jugadores para dejar un equipo
                    return getMatches(args[2], getPlayersList());
                }
                break;

            case "info":
                if (args.length == 2 && sender.hasPermission("xteams.command.info")) {
                    // Mostrar información de todos los equipos disponibles
                    return getMatches(args[1], getTeamsList());
                }
                break;

            case "teaminfo":
                if (args.length == 2 && sender.hasPermission("xteams.command.teaminfo")) {
                    // Mostrar información sobre un equipo
                    return getMatches(args[1], getTeamsList());
                }
                break;

            case "playerinfo":
                if (args.length == 2 && sender.hasPermission("xteams.command.playerinfo")) {
                    // Mostrar información sobre un jugador
                    return getMatches(args[1], getPlayersList());
                }
                break;

            case "list":
                if (args.length == 1 && sender.hasPermission("xteams.command.list")) {
                    return subCommands;
                }
                break;

            default:
                break;
        }

        return Collections.emptyList();  // Retornar vacío si no hay más sugerencias
    }

    // Método auxiliar para filtrar las sugerencias
    private List<String> getMatches(String arg, List<String> options) {
        List<String> matches = new ArrayList<>();
        for (String option : options) {
            if (option.toLowerCase().startsWith(arg.toLowerCase())) {
                matches.add(option);
            }
        }
        return matches;
    }

    // Método para obtener la lista de equipos disponibles
    private List<String> getTeamsList() {
        List<String> teams = new ArrayList<>();
        return teams;
    }

    // Método para obtener la lista de jugadores disponibles en el servidor
    private List<String> getPlayersList() {
        List<String> players = new ArrayList<>();
        // Aquí obtienes los jugadores conectados al servidor
        for (Player player : Bukkit.getOnlinePlayers()) {
            players.add(player.getName());
        }
        return players;
    }
}
