package dev.drygo.XTeams.Commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import dev.drygo.XTeams.Managers.ConfigManager;
import dev.drygo.XTeams.Models.Team;
import dev.drygo.XTeams.Utils.ChatUtils;
import dev.drygo.XTeams.XTeams;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class XTeamsCommand implements CommandExecutor {
    private final XTeams plugin;
    private final ChatUtils chatUtils;
    private final ConfigManager configManager;

    public XTeamsCommand(XTeams plugin) {
        this.plugin = plugin;
        this.chatUtils = plugin.getChatUtils();
        this.configManager = plugin.getConfigManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(chatUtils.getMessage("error.commands.unknown_command", null));
            return false;
        }

        String subcommand = args[0].toLowerCase(Locale.ROOT);

        switch (subcommand) {
            case "create" -> {
                if (dontHavePermission(sender, "xteams.command.create")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission", null));
                    return true;
                }
                return handleCreate(sender, args);
            }
            case "delete" -> {
                if (dontHavePermission(sender, "xteams.command.delete")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission", null));
                    return true;
                }
                return handleDelete(sender, args);
            }
            case "setdisplay" -> {
                if (dontHavePermission(sender, "xteams.command.setdisplay")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission", null));
                    return true;
                }
                return handleSetDisplay(sender, args);
            }
            case "list" -> {
                if (dontHavePermission(sender, "xteams.command.list")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission", null));
                    return true;
                }
                return handleList(sender);
            }
            case "join" -> {
                if (dontHavePermission(sender, "xteams.command.join")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission", null));
                    return true;
                }
                return handleJoin(sender, args);
            }
            case "teaminfo" -> {
                if (dontHavePermission(sender, "xteams.command.teaminfo")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission", null));
                    return true;
                }
                return handleInfoTeam(sender, args);
            }
            case "playerinfo" -> {
                if (dontHavePermission(sender, "xteams.command.playerinfo")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission", null));
                    return true;
                }
                return handleInfoPlayer(sender, args);
            }
            case "leave" -> {
                if (dontHavePermission(sender, "xteams.command.leave")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission", null));
                    return true;
                }
                return handleLeave(sender, args);
            }
            case "reload" -> {
                if (dontHavePermission(sender, "xteams.command.reload")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission", null));
                    return true;
                }
                return handleReload(sender);
            }
            case "info" -> {
                if (dontHavePermission(sender, "xteams.command.info")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission", null));
                    return true;
                }
                return handleInfo(sender);
            }
            case "sync" -> {
                if (dontHavePermission(sender, "xteams.command.sync")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission", null));
                    return true;
                }
                handleSync(sender);
                return false;
            }
            case "help" -> {
                if (dontHavePermission(sender, "xteams.command.help")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission", null));
                    return true;
                }
                return handleHelp(sender);
            }
            default -> {
                sender.sendMessage(chatUtils.getMessage("error.commands.unknown_command", null));
                return false;
            }
        }
    }

    private boolean handleCreate(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(chatUtils.getMessage("error.commands.team_not_specified.create", null));
            return false;
        }
        if (args.length < 3) {
            sender.sendMessage(chatUtils.getMessage("error.commands.priority_not_specified", null));
            return false;
        }

        String teamName = args[1];
        int priority;

        try {
            priority = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(chatUtils.getMessage("error.commands.invalid_priority", null));
            return false;
        }

        Team team = plugin.getTeamManager().getTeamByName(teamName);
        if (team != null) {
            sender.sendMessage(chatUtils.getMessage("error.commands.team_already_exists", null).replace("%team%", teamName));
            return false;
        }

        plugin.getTeamManager().createTeam(teamName, teamName, priority, new HashSet<>());
        sender.sendMessage(chatUtils.getMessage("commands.create.success", null).replace("%team%", teamName));

        return true;
    }

    private boolean handleDelete(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(chatUtils.getMessage("error.commands.team_not_specified.delete", null));
            return false;
        }

        String teamName = args[1];

        if (teamName.equals("*")) {
            if (dontHavePermission(sender, "xteams.command.delete.all")) {
                sender.sendMessage(chatUtils.getMessage("error.commands.all_not_permission.delete", null));
                return false;
            }
            if (plugin.getTeamManager().getAllTeams().isEmpty()) {
                sender.sendMessage(chatUtils.getMessage("error.commands.anyteam_not_found", null));
                return false;
            }
            plugin.getTeamManager().deleteAllTeams();
            sender.sendMessage(chatUtils.getMessage("commands.delete.successall", null));
            configManager.saveTeamsToConfig();
            return true;
        }

        Team team = plugin.getTeamManager().getTeamByName(teamName);
        if (team == null) {
            sender.sendMessage(chatUtils.getMessage("error.commands.team_not_found", null).replace("%team%", teamName));
            return false;
        }

        plugin.getTeamManager().deleteTeam(team);
        sender.sendMessage(chatUtils.getMessage("commands.delete.success", null).replace("%team%", teamName));
        return true;
    }

    private boolean handleSetDisplay(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(chatUtils.getMessage("error.commands.setdisplay.displayname_not_specified", null));
            return false;
        }

        String teamName = args[1];
        String displayName = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

        if (!displayName.startsWith("\"") || !displayName.endsWith("\"")) {
            sender.sendMessage(chatUtils.getMessage("error.commands.setdisplay.invalid_format", null));
            return false;
        }

        displayName = displayName.substring(1, displayName.length() - 1);

        Team team = plugin.getTeamManager().getTeamByName(teamName);
        if (team == null) {
            sender.sendMessage(chatUtils.getMessage("error.commands.team_not_found", null).replace("%team%", teamName));
            return false;
        }

        team.setDisplayName(displayName);

        sender.sendMessage(chatUtils.getMessage("commands.setdisplay.success", null).replace("%team%", teamName).replace("%display_name%", displayName));
        return true;
    }

    private boolean handleInfoTeam(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(chatUtils.getMessage("error.commands.team_not_specified.teaminfo", null));
            return false;
        }

        String teamName = args[1];
        Team team = plugin.getTeamManager().getTeamByName(teamName);
        if (team == null) {
            sender.sendMessage(chatUtils.getMessage("error.commands.team_not_found", null).replace("%team%", teamName));
            return false;
        }

        Map<String, Object> teamInfo = plugin.getTeamManager().getTeamInfo(team);
        String displayName = (String) teamInfo.get("displayName");
        String name = (String) teamInfo.get("name");
        int priority = (int) teamInfo.get("priority");
        @SuppressWarnings("unchecked")
        Set<String> membersSet = (Set<String>) teamInfo.get("members");
        List<String> members = new ArrayList<>(membersSet);

        StringBuilder message = new StringBuilder();
        for (String line : chatUtils.getMessageList("commands.teaminfo.string.header")) {
            line = line.replace("%prefix%", configManager.getPrefix())
                    .replace("%team%", name)
                    .replace("%display_name%", displayName)
                    .replace("%priority%", String.valueOf(priority));
            line = ChatUtils.formatColor(line);
            line = PlaceholderAPI.setPlaceholders(null, line);
            message.append(line).append("\n");
        }

        if (members.isEmpty()) {
            message.append(chatUtils.getMessage("commands.teaminfo.string.no_members", null)).append("\n");
        } else {
            message.append(chatUtils.getMessage("commands.teaminfo.string.members_header", null)).append("\n");
            for (String member : members) {
                message.append(chatUtils.getMessage("commands.teaminfo.string.members_row", null).replace("%member%", member)).append("\n");
            }
        }

        for (String line : chatUtils.getMessageList("commands.teaminfo.string.footer")) {
            line = ChatUtils.formatColor(line);
            line = PlaceholderAPI.setPlaceholders(null, line);
            message.append(line).append("\n");
        }

        sender.sendMessage(message.toString());
        return true;
    }

    private boolean handleInfoPlayer(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(chatUtils.getMessage("error.commands.player_not_found", null).replace("%target%", sender.getName()));
            return false;
        }

        String targetName = args[1];

        List<Team> playerTeams = plugin.getTeamManager().getPlayerTeams(targetName);

        if (playerTeams == null || playerTeams.isEmpty()) {
            sender.sendMessage(chatUtils.getMessage("commands.playerinfo.string.no_teams", null).replace("%target%", targetName));
            return true;
        }

        playerTeams.sort(Comparator.comparingInt(Team::getPriority).reversed());
        Team mainTeam = playerTeams.getFirst();

        StringBuilder message = new StringBuilder();
        for (String line : chatUtils.getMessageList("commands.playerinfo.string.header")) {
            line = line.replace("%target%", targetName)
                    .replace("%prefix%", configManager.getPrefix());
            line = ChatUtils.formatColor(line);
            message.append(line).append("\n");
        }

        message.append(chatUtils.getMessage("commands.playerinfo.string.main_team", null)
                        .replace("%target%", targetName)
                        .replace("%display_name%", mainTeam.getDisplayName())
                        .replace("%team%", mainTeam.getName())
                        .replace("%priority%", String.valueOf(mainTeam.getPriority())))
                .append("\n");

        message.append(chatUtils.getMessage("commands.playerinfo.string.team_list_header", null).replace("%target%", targetName)).append("\n");

        for (Team team : playerTeams) {
            message.append(chatUtils.getMessage("commands.playerinfo.string.team_list_row", null)
                            .replace("%target%", targetName)
                            .replace("%display_name%", team.getDisplayName())
                            .replace("%team%", team.getName())
                            .replace("%priority%", String.valueOf(team.getPriority())))
                    .append("\n");
        }

        for (String line : chatUtils.getMessageList("commands.playerinfo.string.footer")) {
            line = line.replace("%target%", targetName)
                    .replace("%prefix%", configManager.getPrefix());
            line = ChatUtils.formatColor(line);
            message.append(line).append("\n");
        }

        sender.sendMessage(message.toString());
        return true;
    }

    private boolean handleList(CommandSender sender) {
        Set<Team> teams = plugin.getTeamManager().getAllTeams();
        if (teams == null || teams.isEmpty()) {
            sender.sendMessage(chatUtils.getMessage("commands.list.empty", null));
            return false;
        }

        StringBuilder message = new StringBuilder();
        message.append(chatUtils.getMessage("commands.list.string.header", null)).append("\n");
        for (Team team : teams) {
            message.append(chatUtils.getMessage("commands.list.string.row", null)
                            .replace("%team%", team.getName())
                            .replace("%priority%", String.valueOf(team.getPriority()))
                            .replace("%display_name%", team.getDisplayName()))
                    .append("\n");
        }
        sender.sendMessage(message.toString());
        return true;
    }
    private boolean handleJoin(CommandSender sender, String[] args) {
        if (args.length < 2) {
            if (sender instanceof Player player) {
                sender.sendMessage(chatUtils.getMessage("error.commands.team_not_specified.join", null)
                        .replace("%target%", player.getName()));
            } else {
                sender.sendMessage("You must specify a team.");
            }
            return false;
        }

        String teamName = args[1];
        boolean allTeams = teamName.equals("*");

        Set<Team> teamsSet;
        if (allTeams) {
            teamsSet = plugin.getTeamManager().getAllTeams();
            if (teamsSet.isEmpty()) {
                sender.sendMessage(chatUtils.getMessage("error.commands.anyteam_not_found", null));
                return false;
            }
        } else {
            Team team = plugin.getTeamManager().getTeam(teamName);
            if (team == null) {
                sender.sendMessage(chatUtils.getMessage("error.commands.team_not_found", null)
                        .replace("%team%", teamName));
                return false;
            }
            teamsSet = Collections.singleton(team);
        }

        // Sí especifican jugador
        if (args.length > 2) {
            String targetName = args[2];
            boolean allTargets = targetName.equals("*");

            Set<String> affectedPlayers;
            if (allTargets) {
                if (allTeams) {
                    affectedPlayers = plugin.getTeamManager().getAllPlayersInTeams();
                } else {
                    // Solo jugadores en ese equipo
                    Team singleTeam = teamsSet.iterator().next();
                    affectedPlayers = plugin.getTeamManager().getTeamMembers(singleTeam);
                }
            } else {
                affectedPlayers = new HashSet<>();
                affectedPlayers.add(targetName);
            }

            if (affectedPlayers.isEmpty()) {
                String msgKey = allTeams
                        ? "error.commands.join.other.none_team_or_target"
                        : "error.commands.join.other.none_target";

                sender.sendMessage(chatUtils.getMessage(msgKey, null)
                        .replace("%team%", teamName)
                        .replace("%target%", targetName));
                return false;
            }

            for (String playerName : affectedPlayers) {
                for (Team team : teamsSet) {
                    if (!plugin.getTeamManager().isInTeam(playerName, team)) {
                        plugin.getTeamManager().joinTeam(playerName, team);
                    }
                }
            }

            String msgKey;
            if (allTeams && allTargets) {
                msgKey = "commands.join.other.success_all_all";
            } else if (allTeams) {
                msgKey = "commands.join.other.success_target_all";
            } else if (allTargets) {
                msgKey = "commands.join.other.success_all_target";
            } else {
                msgKey = "commands.join.other.success";
            }

            sender.sendMessage(chatUtils.getMessage(msgKey, null)
                    .replace("%team%", teamName)
                    .replace("%target%", targetName));

            configManager.saveTeamsToConfig();
            return true;
        }

        // Si no especifica jugador, debe ser jugador ejecutando el comando
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        }

        if (allTeams) {
            plugin.getTeamManager().joinAllTeams(player.getName());

            player.sendMessage(chatUtils.getMessage("commands.join.self.success_all", null)
                    .replace("%target%", player.getName()));
            configManager.saveTeamsToConfig();
            return true;
        }

        Team team = teamsSet.iterator().next();
        if (plugin.getTeamManager().isInTeam(player.getName(), team)) {
            player.sendMessage(chatUtils.getMessage("error.commands.join.self.already_in_team", null)
                    .replace("%team%", teamName)
                    .replace("%target%", player.getName()));
            return false;
        }

        plugin.getTeamManager().joinTeam(player.getName(), team);
        player.sendMessage(chatUtils.getMessage("commands.join.self.success", null)
                .replace("%team%", teamName)
                .replace("%target%", player.getName()));

        return true;
    }
    private boolean handleLeave(CommandSender sender, String[] args) {
        if (args.length < 2) {
            if (sender instanceof Player player) {
                player.sendMessage(chatUtils.getMessage("error.commands.team_not_specified.leave", null));
            } else {
                sender.sendMessage("You must specify a team.");
            }
            return false;
        }

        String teamName = args[1];
        boolean allTeams = teamName.equals("*");

        Team team = null;
        if (!allTeams) {
            team = plugin.getTeamManager().getTeam(teamName);
            if (team == null) {
                sender.sendMessage(chatUtils.getMessage("error.commands.team_not_found", null)
                        .replace("%team%", teamName));
                return false;
            }
        }

        if (allTeams && dontHavePermission(sender, "xteams.command.leave.all")) {
            sender.sendMessage(chatUtils.getMessage("error.commands.all_not_permission.leave", null));
            return false;
        }

        if (args.length > 2) {
            String targetName = args[2];
            boolean allTargets = targetName.equals("*");

            Set<String> affectedPlayers;

            if (allTargets) {
                affectedPlayers = allTeams
                        ? plugin.getTeamManager().getAllPlayersInTeams()
                        : plugin.getTeamManager().getTeamMembers(team);
            } else {
                affectedPlayers = new HashSet<>();
                affectedPlayers.add(targetName);
            }

            if (affectedPlayers.isEmpty()) {
                String msgKey = allTeams
                        ? "error.commands.leave.other.none_in_anyteam"
                        : "error.commands.leave.other.none_in_team";

                sender.sendMessage(chatUtils.getMessage(msgKey, null)
                        .replace("%team%", teamName)
                        .replace("%target%", targetName));
                return false;
            }

            Set<String> playersCopy = new HashSet<>(affectedPlayers);
            for (String playerName : playersCopy) {
                if (allTeams) {
                    plugin.getTeamManager().leaveAllTeams(playerName);
                } else {
                    plugin.getTeamManager().leaveTeam(playerName, team);
                }
            }

            String msgKey;
            if (allTeams && allTargets) {
                msgKey = "commands.leave.other.all.success_all";
            } else if (allTeams) {
                msgKey = "commands.leave.other.target.success_all";
            } else if (allTargets) {
                msgKey = "commands.leave.other.all.success";
            } else {
                msgKey = "commands.leave.other.target.success";
            }

            sender.sendMessage(chatUtils.getMessage(msgKey, null)
                    .replace("%team%", teamName)
                    .replace("%target%", targetName));

            configManager.saveTeamsToConfig();
            return true;
        }

        // Si no especifica jugador, debe ser jugador que ejecuta el comando
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        }

        boolean inTeam = allTeams
                ? plugin.getTeamManager().isInAnyTeam(player.getName())
                : plugin.getTeamManager().isInTeam(player.getName(), team);

        if (!inTeam) {
            String msgKey = allTeams
                    ? "error.commands.leave.self.not_in_anyteam"
                    : "error.commands.leave.self.not_in_team";

            player.sendMessage(chatUtils.getMessage(msgKey, null)
                    .replace("%team%", teamName)
                    .replace("%target%", player.getName()));
            return false;
        }

        if (allTeams) {
            plugin.getTeamManager().leaveAllTeams(player.getName());
            player.sendMessage(chatUtils.getMessage("commands.leave.self.success_all", null)
                    .replace("%target%", player.getName()));
        } else {
            plugin.getTeamManager().leaveTeam(player.getName(), team);
            player.sendMessage(chatUtils.getMessage("commands.leave.self.success", null)
                    .replace("%team%", teamName)
                    .replace("%target%", player.getName()));
        }

        return true;
    }

    private void handleSync(CommandSender sender) {
        int count = 0;
        for (String teamName : plugin.getTeamManager().listTeams()) {
            Team team = plugin.getTeamManager().getTeam(teamName);
            for (String memberName : team.getMembers()) {
                Player player = Bukkit.getPlayerExact(memberName);
                if (player != null && player.isOnline()) {
                    if (plugin.isEnabledLuckPermsHook()) {
                        plugin.getLuckPermsGroupManager().applyGroup(player, team.getName());
                    }
                    if (plugin.isEnabledMinecraftTeamHook()) {
                        plugin.getMinecraftTeamManager().applyGroup(player, team.getName());
                    }
                    count++;
                }
            }
        }

        sender.sendMessage(chatUtils.getMessage("commands.sync.success", (Player) sender).replace("%count%", String.valueOf(count)));
    }


    private boolean handleReload(CommandSender sender) {
        plugin.reloadConfig();
        configManager.loadMessages();
        configManager.loadTeamsFromConfig();
        plugin.getAutoTeamManager().load();
        sender.sendMessage(chatUtils.getMessage("commands.reload.success", null));
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
                    "&f  /xᴛᴇᴀᴍꜱ ꜱᴇᴛᴅɪꜱᴘʟᴀʏ \"<ᴅɪꜱᴘʟᴀʏ ɴᴀᴍᴇ>\" #707070- #ccccccSets the display name of a team.",
                    "&f  /xᴛᴇᴀᴍꜱ ᴊᴏɪɴ <ᴛᴇᴀᴍ> <ᴘʟᴀʏᴇʀ> #707070- #ccccccJoin a team (leave the player empty to join yourself).",
                    "&f  /xᴛᴇᴀᴍꜱ ʟᴇᴀᴠᴇ <ᴛᴇᴀᴍ / *> <ᴘʟᴀʏᴇʀ / *> #707070- #ccccccLeave a team (leave the player empty to leave yourself).",
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
        String placeholderStatus = plugin.isWorkingPlaceholderAPI() ? "#a0ff72✔" : "#ff7272✖";
        String luckPermsHookStatus = plugin.isEnabledLuckPermsHook() ? "#a0ff72✔" : "#ff7272✖";
        String minecraftTeamHookStatus = plugin.isEnabledMinecraftTeamHook() ? "#a0ff72✔" : "#ff7272✖";
        String autoTeamStatus = plugin.isEnabledAutoTeam() ? "#a0ff72✔" : "#ff7272✖";
        sender.sendMessage(ChatUtils.formatColor("&7"));
        sender.sendMessage(ChatUtils.formatColor("&7"));
        sender.sendMessage(ChatUtils.formatColor("&8                            #ffbaff&lx&r&lTeams &8» &r&fInfo"));
        sender.sendMessage(ChatUtils.formatColor("&7"));
        sender.sendMessage(ChatUtils.formatColor("#fff18d&l                           ᴍᴀᴅᴇ ʙʏ"));
        sender.sendMessage(ChatUtils.formatColor("&f                           xDrygo #707070» &7&o(@eldrygo)"));
        sender.sendMessage(ChatUtils.formatColor("&7"));
        sender.sendMessage(ChatUtils.formatColor("#fff18d&l                  ʀᴜɴɴɪɴɢ ᴘʟᴜɢɪɴ ᴠᴇʀꜱɪᴏɴ"));
        sender.sendMessage(ChatUtils.formatColor("&f                                    " + plugin.version));
        sender.sendMessage(ChatUtils.formatColor("&7"));
        sender.sendMessage(ChatUtils.formatColor("#fff18d&l                      ꜰᴇᴀᴛᴜʀᴇꜱ ᴇɴᴀʙʟᴇᴅ"));
        sender.sendMessage(ChatUtils.formatColor("&f                            ᴘʟᴀᴄᴇʜᴏʟᴅᴇʀᴀᴘɪ #707070» #FFFAAB" + placeholderStatus));
        sender.sendMessage(ChatUtils.formatColor("&f                                ʟᴜᴄᴋᴘᴇʀᴍꜱ #707070» #FFFAAB" + luckPermsHookStatus));
        sender.sendMessage(ChatUtils.formatColor("&f                            ᴍɪɴᴇᴄʀᴀꜰᴛ ᴛᴇᴀᴍꜱ #707070» #FFFAAB" + minecraftTeamHookStatus));
        sender.sendMessage(ChatUtils.formatColor("&f                                ᴀᴜᴛᴏ-ᴛᴇᴀᴍ #707070» #FFFAAB" + autoTeamStatus));
        sender.sendMessage(ChatUtils.formatColor("&7"));
        sender.sendMessage(ChatUtils.formatColor("#fff18d&l                      ᴠᴇʀꜱɪᴏɴ ᴄʜᴀɴɢᴇꜱ"));
        sender.sendMessage(ChatUtils.formatColor("&f            #7070701. #FFFAABFixed plugin loading without LuckPerms installed."));
        sender.sendMessage(ChatUtils.formatColor("&7"));
        sender.sendMessage(ChatUtils.formatColor("#fff18d&l               ᴅʀʏɢᴏ'ꜱ ɴᴏᴛᴇ ᴏꜰ ᴛʜᴇ ᴠᴇʀꜱɪᴏɴ"));
        sender.sendMessage(ChatUtils.formatColor("&f  #FFFAAB        Little fix about plugin loading without LuckPerms."));
        sender.sendMessage(ChatUtils.formatColor("&7"));
        sender.sendMessage(ChatUtils.formatColor("&7"));
        return false;
    }

    private boolean dontHavePermission(CommandSender sender, String perm) {
        return !sender.hasPermission(perm) && !sender.hasPermission("xteams.admin") && !sender.isOp();
    }
}