package org.eldrygo.Commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.eldrygo.Managers.ConfigManager;
import org.eldrygo.Models.Team;
import org.eldrygo.Utils.ChatUtils;
import org.eldrygo.XTeams;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class XTeamsCommand implements CommandExecutor {
    private final XTeams plugin;
    private ChatUtils chatUtils;
    private ConfigManager configManager;

    public XTeamsCommand(XTeams plugin) {
        this.plugin = plugin;
        this.chatUtils = plugin.getChatUtils();
        this.configManager = plugin.getConfigManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(chatUtils.getMessage("error.commands.unknown_command"));
            return false;
        }

        String subcommand = args[0].toLowerCase();

        // Call the commands
        switch (subcommand) {
            case "create":
                if (!sender.hasPermission("xteams.command.create") && !sender.hasPermission("xteams.admin")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission"));
                    return true;
                } else {
                    return handleCreate(sender, args);
                }
            case "delete":
                if (!sender.hasPermission("xteams.command.delete") && !sender.hasPermission("xteams.admin")) {
                } else {
                    return handleDelete(sender, args);
                }
            case "setdisplay":
                if (!sender.hasPermission("xteams.command.setdisplay") && !sender.hasPermission("xteams.admin")) {
                } else {
                    return handleSetDisplay(sender, args);
                }
            case "list":
                if (!sender.hasPermission("xteams.command.list") && !sender.hasPermission("xteams.admin")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission"));
                    return true;
                } else {
                    return handleList(sender);
                }
            case "join":
                if (!sender.hasPermission("xteams.command.join") && !sender.hasPermission("xteams.admin")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission"));
                    return true;
                } else {
                    return handleJoin(sender, args);
                }
            case "teaminfo":
                if (!sender.hasPermission("xteams.command.teaminfo") && !sender.hasPermission("xteams.admin")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission"));
                    return true;
                } else {
                    return handleInfoTeam(sender, args);
                }
            case "playerinfo":
                if (!sender.hasPermission("xteams.command.playerinfo") && !sender.hasPermission("xteams.admin")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission"));
                    return true;
                } else {
                    return handleInfoPlayer(sender, args);
                }

            case "leave":
                if (!sender.hasPermission("xteams.command.leave") && !sender.hasPermission("xteams.admin")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission"));
                    return true;
                } else {
                    return handleLeave(sender, args);
                }
            case "reload":
                if (!sender.hasPermission("xteams.command.reload") && !sender.hasPermission("xteams.admin")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission"));
                    return true;
                } else {
                    return handleReload(sender);
                }
            case "info":
                if (!sender.hasPermission("xteams.command.info") && !sender.hasPermission("xteams.admin")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission"));
                    return true;
                } else {
                    return handleInfo(sender);
                }
            case "help":
                if (!sender.hasPermission("xteams.command.help") && !sender.hasPermission("xteams.admin")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission"));
                    return true;
                } else {
                    return handleHelp(sender);
                }
            default:
                sender.sendMessage(chatUtils.getMessage("error.commands.unknown_command"));
                return false;
        }
    }

    private boolean handleCreate(CommandSender sender, String[] args) {
        if (args.length == 1) { // Ahora se requieren al menos 3 argumentos
            sender.sendMessage(chatUtils.getMessage("error.commands.team_not_specified.create"));
            return false;
        } else if (args.length == 2) {
            sender.sendMessage(chatUtils.getMessage("error.commands.create.priority_not_specified"));
            return false;
        }

        String teamName = args[1];
        int priority;

        try {
            priority = Integer.parseInt(args[2]); // Intenta convertir el argumento en un número
        } catch (NumberFormatException e) {
            sender.sendMessage(chatUtils.getMessage("error.commands.invalid_priority"));
            return false;
        }

        Team team = plugin.getTeamManager().getTeamByName(teamName);
        if (team != null) {
            sender.sendMessage(chatUtils.getMessage("error.commands.team_already_exists")
                    .replace("%team%", teamName));
            return false;
        }

        plugin.getTeamManager().createTeam(teamName, teamName, priority, new HashSet<>());
        sender.sendMessage(chatUtils.getMessage("commands.create.success")
                .replace("%team%", teamName));
        configManager.saveTeamsToConfig();

        return true;
    }

    private boolean handleDelete(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(chatUtils.getMessage("error.commands.team_not_specified.delete"));
            return false;
        }

        String teamName = args[1];
        Team team = plugin.getTeamManager().getTeamByName(teamName);
        // Remove a team logic
        if (teamName.equals("*")) {
            if (sender.hasPermission("xteams.command.delete.*")) {
                plugin.getTeamManager().deleteAllTeams(); // No return value
                sender.sendMessage(chatUtils.getMessage("commands.delete.successall"));
                configManager.saveTeamsToConfig();
            } else {
                sender.sendMessage(chatUtils.getMessage("error.commands.no_permission"));
                return true;
            }
        } else {
            if (team == null) {
                // Si el equipo no existe, enviamos el mensaje de error
                sender.sendMessage(chatUtils.getMessage("error.commands.team_not_found")
                        .replace("%team%", teamName));
                return false;
            } else {
            plugin.getTeamManager().deleteTeam(teamName); // No return value
            sender.sendMessage(chatUtils.getMessage("commands.delete.success")
                    .replace("%team%", teamName));
            configManager.saveTeamsToConfig();
            }
        }
        return true;
    }
    private boolean handleSetDisplay(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(chatUtils.getMessage("error.commands.team_not_specified.setdisplay"));
            return false;
        } else if (args.length < 3) {
            sender.sendMessage(chatUtils.getMessage("error.commands.setdisplay.displayname_not_specified"));
            return false;
        }

        String teamName = args[1]; // El nombre del equipo
        String displayName = String.join(" ", Arrays.copyOfRange(args, 2, args.length)); // Captura el nombre con espacios

        // Verificar si el displayName está entre comillas
        if (!displayName.startsWith("\"") || !displayName.endsWith("\"")) {
            sender.sendMessage(chatUtils.getMessage("error.commands.setdisplay.invalid_format"));
            return false;
        }

        // Eliminar las comillas alrededor del display name
        displayName = displayName.substring(1, displayName.length() - 1);

        Team team = plugin.getTeamManager().getTeamByName(teamName);
        if (team == null) {
            sender.sendMessage(chatUtils.getMessage("error.commands.team_not_found"));
            return false;
        }

        // Establecer el nuevo display name
        team.setDisplayName(displayName);

        // Guardar el equipo en la configuración
        configManager.saveTeamsToConfig();

        sender.sendMessage(chatUtils.getMessage("commands.setdisplay.success")
                .replace("%team%", teamName).replace("%display_name%", displayName));

        return true;
    }

    private boolean handleJoin(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(chatUtils.getMessage("error.commands.team_not_specified.join"));
            return false;
        }

        String teamName = args[1];
        Team team = plugin.getTeamManager().getTeamByName(teamName);

        // Comprobación si el sender es un jugador
        if (!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(chatUtils.getMessage("error.commands.join.self_onlyplayer"));
            return false;
        }

        Player player = (Player) sender;
        Player targetPlayer = Bukkit.getPlayer(args[2]); // Obtener el jugador especificado
        String playerName = targetPlayer.getName();

        // Verificar si se especifica un tercer argumento (el jugador al que se le va a agregar al equipo)
        if (args.length > 2) {

            if (targetPlayer == null || !targetPlayer.isOnline()) {
                // Si el jugador especificado no está conectado, enviar mensaje de error
                sender.sendMessage(chatUtils.getMessage("error.commands.player_not_found")
                        .replace("%player%", playerName));
                return false;
            }

            // Comprobar si el jugador ya está en el equipo
            if (plugin.getTeamManager().isInTeam(targetPlayer, teamName)) {
                sender.sendMessage(chatUtils.getMessage("error.commands.join.other.already_in_team")
                        .replace("%player%", playerName)
                        .replace("%team%", teamName));
                return false;
            }

            // Si el jugador está online y no está en el equipo, lo agregamos
            if (team == null) {
                // Si el equipo no existe, enviamos el mensaje de error
                sender.sendMessage(chatUtils.getMessage("error.commands.team_not_found")
                        .replace("%team%", teamName));
                return false;
            } else {
                plugin.getTeamManager().joinTeam(targetPlayer, teamName); // Agregar al jugador especificado al equipo
                sender.sendMessage(chatUtils.getMessage("commands.join.other.success")
                        .replace("%team%", teamName)
                        .replace("%player%", playerName));
                configManager.saveTeamsToConfig();
            }
        } else {
            // Si no se especifica un tercero, unimos al jugador que ejecuta el comando

            // Comprobar si el jugador ya está en el equipo
            if (plugin.getTeamManager().isInTeam(player, teamName)) {
                sender.sendMessage(chatUtils.getMessage("error.commands.join.self.already_in_team")
                        .replace("%team%", teamName));
                return false;
            }

            // Unir al jugador que ejecuta el comando al equipo
            if (team == null) {
                // Si el equipo no existe, enviamos el mensaje de error
                sender.sendMessage(chatUtils.getMessage("error.commands.team_not_found")
                        .replace("%team%", teamName));
                return false;
            } else {
                plugin.getTeamManager().joinTeam(player, teamName); // Unir al jugador que ejecuta el comando
                sender.sendMessage(chatUtils.getMessage("commands.join.self.success")
                        .replace("%team%", teamName));
                configManager.saveTeamsToConfig();
            }
        }
        return true;
    }

    private boolean handleInfoTeam(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(chatUtils.getMessage("error.commands.team_not_specified.teaminfo"));
            return false;
        }

        String teamName = args[1];
        // Get team information using TeamManager
        Map<String, Object> teamInfo = plugin.getTeamManager().getTeamInfo(teamName);
        Team team = plugin.getTeamManager().getTeamByName(teamName);
        if (team == null) {
            sender.sendMessage(chatUtils.getMessage("error.commands.team_not_found")
                    .replace("%team%", teamName));
            return false;
        }

        // Get team information
        String displayName = (String) teamInfo.get("displayName");
        String name = (String) teamInfo.get("name");
        int priority = (int) teamInfo.get("priority");
        // Convert HashSet to List if necessary
        Set<String> membersSet = (Set<String>) teamInfo.get("members");
        List<String> members = new ArrayList<>(membersSet);  // Convert the Set to a List

        StringBuilder message = new StringBuilder();

        // Add the header from messages.yml
        List<String> headerLines = chatUtils.getMessageList("commands.teaminfo.string.header");
        if (headerLines.isEmpty()) {
            message.append("No header lines found in the configuration.\n"); // Debugging message
        }
        for (String line : headerLines) {
            // Apply color formatting to each line and replace placeholders
            line = chatUtils.formatColor(line.replace("%display_name%", displayName)
                    .replace("%team%", name)
                    .replace("%prefix%", plugin.prefix)
                    .replace("%priority%", String.valueOf(priority)));
            message.append(PlaceholderAPI.setPlaceholders((OfflinePlayer) sender, line))  // Reemplaza los placeholders
                    .append("\n");  // Add newline after each header line
        }

        // Add Members
        if (members.isEmpty()) {
            message.append(chatUtils.getMessage("commands.teaminfo.string.no_members")).append("\n");
        } else {
            message.append(chatUtils.getMessage("commands.teaminfo.string.members_header")).append("\n");
            for (String member : members) {
                message.append(chatUtils.getMessage("commands.teaminfo.string.members_row")
                        .replace("%member%", member)).append("\n");  // Add newline after each member
            }
        }

        // Add the footer from messages.yml
        List<String> footerLines = chatUtils.getMessageList("commands.teaminfo.string.footer");
        if (footerLines.isEmpty()) {
            message.append("No footer lines found in the configuration.\n"); // Debugging message
        }
        for (String line : footerLines) {
            // Apply color formatting to each footer line and replace placeholders
            message.append(PlaceholderAPI.setPlaceholders((OfflinePlayer) sender, chatUtils.formatColor(line))).append("\n");
        }

        sender.sendMessage(message.toString());
        return true;
    }
    private boolean handleInfoPlayer(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(chatUtils.getMessage("error.commands.player_not_specified"));
            return false;
        }

        Player player = (Player) sender;
        Player targetPlayer = Bukkit.getPlayer(args[2]); // Obtener el jugador especificado
        String playerName = targetPlayer.getName();

        if (targetPlayer == null || (!targetPlayer.hasPlayedBefore() && !targetPlayer.isOnline())) {
            sender.sendMessage(chatUtils.getMessage("error.commands.player_not_found")
                    .replace("%player%", playerName));
            return false;
        }

        List<Team> playerTeams = plugin.getTeamManager().getPlayerTeams(targetPlayer);

        if (playerTeams.isEmpty()) {
            sender.sendMessage(chatUtils.getMessage("commands.playerinfo.string.no_teams")
                    .replace("%player%", playerName));
            return true;
        }

        // Ordenar por prioridad descendente
        playerTeams.sort((a, b) -> Integer.compare(b.getPriority(), a.getPriority()));

        // Obtener el equipo con mayor prioridad
        Team mainTeam = playerTeams.get(0);

        // Construir el mensaje informativo
        StringBuilder message = new StringBuilder();
        message.append(chatUtils.getMessage("commands.playerinfo.string.header")
                        .replace("%player%", playerName)
                        .replace("%prefix%", plugin.prefix))
                .append("\n");
        message.append(chatUtils.getMessage("commands.playerinfo.string.main_team")
                .replace("%display_name%", mainTeam.getDisplayName())
                .replace("%team%", mainTeam.getName())).append("\n");

        message.append(chatUtils.getMessage("commands.playerinfo.string.team_list_header")).append("\n");
        for (Team team : playerTeams) {
            message.append(chatUtils.getMessage("commands.playerinfo.string.team_list_row")
                    .replace("%display_name%", team.getDisplayName())
                    .replace("%team%", team.getName())
                    .replace("%priority%", String.valueOf(team.getPriority()))).append("\n");
        }

        List<String> footerLines = chatUtils.getMessageList("commands.playerinfo.string.footer");
        if (footerLines.isEmpty()) {
            message.append("No footer lines found in the configuration.\n"); // Debugging message
        }
        for (String line : footerLines) {
            // Apply color formatting to each footer line and replace placeholders
            message.append(PlaceholderAPI.setPlaceholders((OfflinePlayer) sender, chatUtils.formatColor(line))).append("\n");
        }

        sender.sendMessage(message.toString());
        return true;
    }

    private boolean handleList(CommandSender sender) {
        // Obtener la lista de equipos (ahora es un Set en lugar de una List)
        Set<Team> teams = plugin.getTeamManager().getAllTeams(); // Cambié el tipo a Set
        if (teams == null || teams.isEmpty()) {
            sender.sendMessage(chatUtils.getMessage("commands.list.empty"));
        } else {
            StringBuilder message = new StringBuilder();
            String header = chatUtils.getMessage("commands.list.string.header");
            message.append(header).append("\n");

            for (Team team : teams) {
                // Aquí usamos %team% para el nombre interno del equipo y %team_displayName% para el nombre visible
                String teamMessage = chatUtils.getMessage("commands.list.string.row")
                        .replace("%team%", team.getName())  // Reemplaza %team% con el nombre interno del equipo
                        .replace("%priority%", String.valueOf(team.getPriority()))  // Reemplaza %team% con el nombre interno del equipo
                        .replace("%display_name%", team.getDisplayName());
                message.append(teamMessage).append("\n");
            }

            sender.sendMessage(message.toString());
            return true;
        }
        return false;
    }


    private boolean handleLeave(CommandSender sender, String[] args) {
        // Verificar si el primer argumento es válido (el nombre del equipo)
        if (args.length == 1) {
            sender.sendMessage(chatUtils.getMessage("error.commands.team_not_specified.leave"));
            return false;
        }

        String teamName = args[1];  // Nombre del equipo
        Team team = plugin.getTeamManager().getTeamByName(teamName);

        // Comprobación si el sender es un jugador (no debe ser null)
        if (!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(chatUtils.getMessage("error.commands.onlyplayer.leave"));
            return false;
        }

        Player player = (Player) sender;
        Player targetPlayer = Bukkit.getPlayer(args[2]); // Obtener el jugador especificado
        String playerName = targetPlayer.getName();

        // Si se especifica "*" como el nombre del equipo, debe manejarse como un caso especial (todos los equipos)
        if (teamName.equals("*")) {
            if (args.length > 2) { // Si hay un tercer argumento, se intenta usar otro jugador

                // Verificar si el jugador especificado está conectado
                if (targetPlayer == null) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.player_not_found")
                            .replace("%player%", playerName));
                    return false;
                }

                if (!plugin.getTeamManager().isInAnyTeam(targetPlayer)) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.leave.other.not_in_anyteam")
                            .replace("%player%", playerName));
                    return false;
                }

                plugin.getTeamManager().leaveAllTeams(targetPlayer); // El jugador especificado sale de todos los equipos
                sender.sendMessage(chatUtils.getMessage("commands.leave.other.successall")
                        .replace("%player%", playerName));
                configManager.saveTeamsToConfig();
            } else { // Si no se especifica un jugador, el jugador que ejecuta el comando sale de todos los equipos
                if (!plugin.getTeamManager().isInAnyTeam(player)) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.leave.self.not_in_anyteam")
                            .replace("%player%", playerName));
                    return false;
                }
                plugin.getTeamManager().leaveAllTeams(player); // El jugador ejecutando el comando sale de todos los equipos
                sender.sendMessage(chatUtils.getMessage("commands.leave.self.successall"));
                configManager.saveTeamsToConfig();
            }
        } else { // Si el nombre del equipo no es "*"
            if (team == null) {
                sender.sendMessage(chatUtils.getMessage("error.commands.team_not_found")
                        .replace("%team%", teamName));
                return false;
            }

            // Si hay un tercer argumento, se maneja como otro jugador del cual se desea salir
            if (args.length > 2) {

                // Verificar si el jugador especificado está conectado
                if (targetPlayer == null) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.player_not_found")
                            .replace("%player%", playerName));
                    return false;
                }

                if (!plugin.getTeamManager().isInTeam(targetPlayer, teamName)) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.leave.other.not_in_team")
                            .replace("%player%",playerName)
                            .replace("%team%", teamName));
                    return false;
                }

                plugin.getTeamManager().leaveTeam(targetPlayer, teamName); // El jugador especificado sale del equipo
                sender.sendMessage(chatUtils.getMessage("commands.leave.other.success")
                        .replace("%player%", playerName)
                        .replace("%team%", teamName));
                configManager.saveTeamsToConfig();
            } else { // Si no se especifica un tercer argumento, el jugador ejecutando el comando sale del equipo
                if (!plugin.getTeamManager().isInTeam(player, teamName)) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.leave.not_in_team")
                            .replace("%player%", playerName)
                            .replace("%team%", teamName));
                    return false;
                }

                plugin.getTeamManager().leaveTeam(player, teamName); // El jugador sale del equipo
                sender.sendMessage(chatUtils.getMessage("commands.leave.self.success")
                        .replace("%team%", teamName));
                configManager.saveTeamsToConfig();
            }
        }
        return true;
    }
    private boolean handleReload(CommandSender sender) {
        plugin.reloadConfig();
        configManager.loadMessages();
        configManager.loadTeamsFromConfig();
        sender.sendMessage(chatUtils.getMessage("commands.reload.success"));
        return false;
    }
    private boolean handleHelp(CommandSender sender) {
        List<String> helpMessages = chatUtils.getMessageConfig().getStringList("commands.help");

        if (helpMessages.isEmpty()) {
            helpMessages = List.of(
                    " ",
                    " ",
                    "                            #ffbaff&lx&r&lTeams &8» &r&fHelp",
                    " ",
                    "                    #fff18d&lᴘʟᴜɢɪɴ ᴄᴏᴍᴍᴀɴᴅꜱ",
                    "&f  /xᴛᴇᴀᴍꜱ ʜᴇʟᴘ #707070» #ccccccShows this help message",
                    "&f  /xᴛᴇᴀᴍꜱ ʀᴇʟᴏᴀᴅ #707070» #ccccccReloads the plugin configuration",
                    "&f  /xᴛᴇᴀᴍꜱ ɪɴꜰᴏ #707070- #ccccccDisplays information about the plugin.",
                    " ",
                    "                       #fff18d&lᴛᴇᴀᴍꜱ ᴄᴏᴍᴍᴀɴᴅꜱ",
                    "&f  /xᴛᴇᴀᴍꜱ ᴄʀᴇᴀᴛᴇ <ᴛᴇᴀᴍ> <ᴘʀɪᴏʀɪᴛʏ> #707070- #ccccccCreate a team.",
                    "&f  /xᴛᴇᴀᴍꜱ ᴅᴇʟᴇᴛᴇ <ᴛᴇᴀᴍ> #707070- #ccccccDelete a team (type * on <team> for delete all the teams).",
                    "&f  /xᴛᴇᴀᴍꜱ ᴊᴏɪɴ <ᴛᴇᴀᴍ> <ᴘʟᴀʏᴇʀ> #707070- #ccccccJoin a team (leave the player empty to join yourself).",
                    "&f  /xᴛᴇᴀᴍꜱ ʟᴇᴀᴠᴇ <ᴛᴇᴀᴍ> <ᴘʟᴀʏᴇʀ> #707070- #ccccccLeave a team (leave the player empty to leave yourself).",
                    " ",
                    "                 #fff18d&lɪɴꜰᴏʀᴍᴀᴛɪᴏɴ ᴄᴏᴍᴍᴀɴᴅꜱ",
                    "&f  /xᴛᴇᴀᴍꜱ ʟɪꜱᴛ #707070- #ccccccDisplays the list of teams registered.",
                    "&f  /xᴛᴇᴀᴍꜱ ᴛᴇᴀᴍɪɴꜰᴏ <ᴛᴇᴀᴍ> #707070- #ccccccGet information about a team.",
                    "&f  /xᴛᴇᴀᴍꜱ ᴘʟᴀʏᴇʀɪɴꜰᴏ <ᴘʟᴀʏᴇʀ> #707070- #ccccccGet information about a player.",
                    " ",
                    " "
            );
        }

        for (String line : helpMessages) {
            String formattedLine = PlaceholderAPI.setPlaceholders((OfflinePlayer) sender, line);
            sender.sendMessage(ChatUtils.formatColor(formattedLine));
        }
        return false;
    }
    private boolean handleInfo(CommandSender sender) {
        String placeholderStatus = plugin.isPlaceholderAPIEnabled() ? "#a0ff72✔" : "#ff7272✖";
        sender.sendMessage(ChatUtils.formatColor("#666666+==================================================+"));
        sender.sendMessage(ChatUtils.formatColor("&7"));
        sender.sendMessage(ChatUtils.formatColor("&8                            #ffbaff&lx&r&lTeams &8» &r&fInfo"));
        sender.sendMessage(ChatUtils.formatColor("&7"));
        sender.sendMessage(ChatUtils.formatColor("#fff18d&l                           ᴍᴀᴅᴇ ʙʏ"));
        sender.sendMessage(ChatUtils.formatColor("&f                           xDrygo #707070» &7&o(@eldrygo)"));;
        sender.sendMessage(ChatUtils.formatColor("&7"));
        sender.sendMessage(ChatUtils.formatColor("#fff18d&l                  ʀᴜɴɴɪɴɢ ᴘʟᴜɢɪɴ ᴠᴇʀꜱɪᴏɴ"));
        sender.sendMessage(ChatUtils.formatColor("&f                                    " + plugin.version));;
        sender.sendMessage(ChatUtils.formatColor("&7"));
        sender.sendMessage(ChatUtils.formatColor("#fff18d&l                      ꜰᴇᴀᴛᴜʀᴇꜱ ᴇɴᴀʙʟᴇᴅ"));
        sender.sendMessage(ChatUtils.formatColor("&f                           ᴘʟᴀᴄᴇʜᴏʟᴅᴇʀᴀᴘɪ #707070» #FFFAAB" + placeholderStatus));
        sender.sendMessage(ChatUtils.formatColor("&7"));
        sender.sendMessage(ChatUtils.formatColor("#fff18d&l                      ᴠᴇʀꜱɪᴏɴ ᴄʜᴀɴɢᴇꜱ"));
        sender.sendMessage(ChatUtils.formatColor("&f      #7070701. #FFFAABCreated xTeams plugin."));
        sender.sendMessage(ChatUtils.formatColor("&f      #7070701. #FFFAABAdded teams system."));
        sender.sendMessage(ChatUtils.formatColor("&f      #7070701. #FFFAABAdded teams management commands."));
        sender.sendMessage(ChatUtils.formatColor("&f      #7070701. #FFFAABAdded teams and player info. commands."));
        sender.sendMessage(ChatUtils.formatColor("&f      #7070701. #FFFAABAdded plugin information commands. (/xteams help/info)"));
        sender.sendMessage(ChatUtils.formatColor("&f      #7070701. #FFFAABAdded reload command."));
        sender.sendMessage(ChatUtils.formatColor("&f      #7070701. #FFFAABAdded PlaceholderAPI placeholders."));
        sender.sendMessage(ChatUtils.formatColor("&f      #7070701. #FFFAABAdded configuration files."));
        sender.sendMessage(ChatUtils.formatColor("&f      #7070701. #FFFAABAdded xTeams API."));
        sender.sendMessage(ChatUtils.formatColor("&7"));
        sender.sendMessage(ChatUtils.formatColor("#fff18d&l               ᴅʀʏɢᴏ'ꜱ ɴᴏᴛᴇ ᴏꜰ ᴛʜᴇ ᴠᴇʀꜱɪᴏɴ"));
        sender.sendMessage(ChatUtils.formatColor("&f  #FFFAAB       Hi, I made this plugin to manage a team system"));
        sender.sendMessage(ChatUtils.formatColor("&f  #FFFAAB       for a proyect I have, I hope it helps you and"));
        sender.sendMessage(ChatUtils.formatColor("&f  #FFFAAB         if you hace any question, dm me on X. Enjoy!"));
        sender.sendMessage(ChatUtils.formatColor("&7"));
        sender.sendMessage(ChatUtils.formatColor("#666666+==================================================+"));
        return false;
    }
}