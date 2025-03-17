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
            sender.sendMessage(chatUtils.getMessage("error.commands.unknown_command"));
            return false;
        }

        String subcommand = args[0].toLowerCase();

        // Call the commands
        switch (subcommand) {
            case "create" -> {
                if (!sender.hasPermission("xteams.command.create") && !sender.hasPermission("xteams.admin")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission"));
                    return true;
                } else {
                    return handleCreate(sender, args);
                }
            }
            case "delete" -> {
                if (!sender.hasPermission("xteams.command.delete") && !sender.hasPermission("xteams.admin")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission"));
                    return true;
                } else {
                    return handleDelete(sender, args);
                }
            }
            case "setdisplay" -> {
                if (!sender.hasPermission("xteams.command.setdisplay") && !sender.hasPermission("xteams.admin")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission"));
                    return true;
                } else {
                    return handleSetDisplay(sender, args);
                }
            }
            case "list" -> {
                if (!sender.hasPermission("xteams.command.list") && !sender.hasPermission("xteams.admin")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission"));
                    return true;
                } else {
                    return handleList(sender);
                }
            }
            case "join" -> {
                if (!sender.hasPermission("xteams.command.join") && !sender.hasPermission("xteams.admin")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission"));
                    return true;
                } else {
                    return handleJoin(sender, args);
                }
            }
            case "teaminfo" -> {
                if (!sender.hasPermission("xteams.command.teaminfo") && !sender.hasPermission("xteams.admin")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission"));
                    return true;
                } else {
                    return handleInfoTeam(sender, args);
                }
            }
            case "playerinfo" -> {
                if (!sender.hasPermission("xteams.command.playerinfo") && !sender.hasPermission("xteams.admin")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission"));
                    return true;
                } else {
                    return handleInfoPlayer(sender, args);
                }
            }
            case "leave" -> {
                if (!sender.hasPermission("xteams.command.leave") && !sender.hasPermission("xteams.admin")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission"));
                    return true;
                } else {
                    return handleLeave(sender, args);
                }
            }
            case "reload" -> {
                if (!sender.hasPermission("xteams.command.reload") && !sender.hasPermission("xteams.admin")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission"));
                    return true;
                } else {
                    return handleReload(sender);
                }
            }
            case "info" -> {
                if (!sender.hasPermission("xteams.command.info") && !sender.hasPermission("xteams.admin")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission"));
                    return true;
                } else {
                    return handleInfo(sender);
                }
            }
            case "help" -> {
                if (!sender.hasPermission("xteams.command.help") && !sender.hasPermission("xteams.admin")) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.no_permission"));
                    return true;
                } else {
                    return handleHelp(sender);
                }
            }
            default -> {
                sender.sendMessage(chatUtils.getMessage("error.commands.unknown_command"));
                return false;
            }
        }
    }

    private boolean handleCreate(CommandSender sender, String[] args) {
        if (args.length < 3) { // Ahora se requieren al menos 3 argumentos
            sender.sendMessage(chatUtils.getMessage("error.commands.team_not_specified.create"));
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
        if (args.length < 3) {
            sender.sendMessage(chatUtils.getMessage("error.commands.setdisplay.displayname_not_specified"));
            return false;
        }

        String teamName = args[1];
        String displayName = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

        if (!displayName.startsWith("\"") || !displayName.endsWith("\"")) {
            sender.sendMessage(chatUtils.getMessage("error.commands.setdisplay.invalid_format"));
            return false;
        }

        displayName = displayName.substring(1, displayName.length() - 1);

        Team team = plugin.getTeamManager().getTeamByName(teamName);
        if (team == null) {
            sender.sendMessage(chatUtils.getMessage("error.commands.team_not_found")
                    .replace("%team%", teamName));
            return false;
        }

        team.setDisplayName(displayName);
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

        if (!(sender instanceof Player player)) {
            Bukkit.getConsoleSender().sendMessage(chatUtils.getMessage("error.commands.join.self_onlyplayer"));
            return false;
        }

        if (args.length > 2) {
            String targetPlayerName = args[2];
            Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

            if (targetPlayer == null || !targetPlayer.isOnline()) {
                sender.sendMessage(chatUtils.getMessage("error.commands.player_not_found")
                        .replace("%player%", targetPlayer.getName()));
                return false;
            }

            if (plugin.getTeamManager().isInTeam(targetPlayer, teamName)) {
                sender.sendMessage(chatUtils.getMessage("error.commands.join.other_already_in_team")
                        .replace("%player%", targetPlayer.getName())
                        .replace("%team%", teamName));
                return false;
            }

            if (team == null) {
                sender.sendMessage(chatUtils.getMessage("error.commands.team_not_found")
                        .replace("%team%", teamName));
                return false;
            } else {
                plugin.getTeamManager().joinTeam(targetPlayer, teamName);
                sender.sendMessage(chatUtils.getMessage("commands.join.other.success")
                        .replace("%team%", teamName)
                        .replace("%player%", targetPlayer.getName()));
                configManager.saveTeamsToConfig();
            }
        } else {
            if (plugin.getTeamManager().isInTeam(player, teamName)) {
                sender.sendMessage(chatUtils.getMessage("error.commands.join.self_already_in_team")
                        .replace("%team%", teamName));
                return false;
            }

            if (team == null) {
                sender.sendMessage(chatUtils.getMessage("error.commands.team_not_found")
                        .replace("%team%", teamName));
                return false;
            } else {
                plugin.getTeamManager().joinTeam(player, teamName);
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
        Map<String, Object> teamInfo = plugin.getTeamManager().getTeamInfo(teamName);
        Team team = plugin.getTeamManager().getTeamByName(teamName);
        if (team == null) {
            sender.sendMessage(chatUtils.getMessage("error.commands.team_not_found")
                    .replace("%team%", teamName));
            return false;
        }

        String displayName = (String) teamInfo.get("displayName");
        String name = (String) teamInfo.get("name");
        int priority = (int) teamInfo.get("priority");
        Set<String> membersSet = (Set<String>) teamInfo.get("members");
        List<String> members = new ArrayList<>(membersSet);

        StringBuilder message = new StringBuilder();

        List<String> headerLines = chatUtils.getMessageList("commands.teaminfo.string.header");
        for (String line : headerLines) {
            line = ChatUtils.formatColor(line.replace("%display_name%", displayName)
                    .replace("%team%", name)
                    .replace("%prefix%", plugin.prefix)
                    .replace("%priority%", String.valueOf(priority)));
            message.append(PlaceholderAPI.setPlaceholders((OfflinePlayer) sender, line))
                    .append("\n");
        }

        if (members.isEmpty()) {
            message.append(chatUtils.getMessage("commands.teaminfo.string.no_members")).append("\n");
        } else {
            message.append(chatUtils.getMessage("commands.teaminfo.string.members_header")).append("\n");
            for (String member : members) {
                message.append(chatUtils.getMessage("commands.teaminfo.string.members_row")
                        .replace("%member%", member)).append("\n");
            }
        }

        List<String> footerLines = chatUtils.getMessageList("commands.teaminfo.string.footer");
        for (String line : footerLines) {
            message.append(PlaceholderAPI.setPlaceholders((OfflinePlayer) sender, ChatUtils.formatColor(line))).append("\n");
        }

        sender.sendMessage(message.toString());
        return true;
    }

    private boolean handleInfoPlayer(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(chatUtils.getMessage("error.commands.player_not_specified"));
            return false;
        }

        String targetName = args[1];
        OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(targetName);

        if (!targetPlayer.hasPlayedBefore() && !targetPlayer.isOnline()) {
            sender.sendMessage(chatUtils.getMessage("error.commands.player_not_found")
                    .replace("%player%", targetPlayer.getName()));
            return false;
        }

        List<Team> playerTeams = plugin.getTeamManager().getPlayerTeams(targetPlayer);

        if (playerTeams.isEmpty()) {
            sender.sendMessage(chatUtils.getMessage("commands.playerinfo.string.no_teams")
                    .replace("%player%", targetPlayer.getName()));
            return true;
        }

        playerTeams.sort((a, b) -> Integer.compare(b.getPriority(), a.getPriority()));

        Team mainTeam = playerTeams.get(0);

        StringBuilder message = new StringBuilder();
        message.append(chatUtils.getMessage("commands.playerinfo.string.header")
                        .replace("%player%", targetPlayer.getName())
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
        for (String line : footerLines) {
            message.append(PlaceholderAPI.setPlaceholders((OfflinePlayer) sender, ChatUtils.formatColor(line))).append("\n");
        }

        sender.sendMessage(message.toString());
        return true;
    }

    private boolean handleList(CommandSender sender) {
        Set<Team> teams = plugin.getTeamManager().getAllTeams();
        if (teams == null || teams.isEmpty()) {
            sender.sendMessage(chatUtils.getMessage("commands.list.empty"));
        } else {
            StringBuilder message = new StringBuilder();
            String header = chatUtils.getMessage("commands.list.string.header");
            message.append(header).append("\n");

            for (Team team : teams) {
                String teamMessage = chatUtils.getMessage("commands.list.string.row")
                        .replace("%team%", team.getName())
                        .replace("%priority%", String.valueOf(team.getPriority()))
                        .replace("%display_name%", team.getDisplayName());
                message.append(teamMessage).append("\n");
            }

            sender.sendMessage(message.toString());
            return true;
        }
        return false;
    }

    private boolean handleLeave(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(chatUtils.getMessage("error.commands.team_not_specified.leave"));
            return false;
        }

        String teamName = args[1];
        Team team = plugin.getTeamManager().getTeamByName(teamName);

        if (!(sender instanceof Player player)) {
            Bukkit.getConsoleSender().sendMessage(chatUtils.getMessage("error.commands.onlyplayer.leave"));
            return false;
        }

        if (teamName.equals("*")) {
            if (args.length > 2) {
                String targetPlayerName = args[2];
                Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

                if (targetPlayer == null) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.player_not_found")
                            .replace("%player%", targetPlayer.getName()));
                    return false;
                }

                if (!plugin.getTeamManager().isInAnyTeam(targetPlayer)) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.leave.other.not_in_anyteam")
                            .replace("%player%", targetPlayer.getName()));
                    return false;
                }

                plugin.getTeamManager().leaveAllTeams(targetPlayer);
                sender.sendMessage(chatUtils.getMessage("commands.leave.other.successall")
                        .replace("%player%", targetPlayer.getName()));
                configManager.saveTeamsToConfig();
            } else {
                if (!plugin.getTeamManager().isInAnyTeam(player)) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.leave.self.not_in_anyteam")
                            .replace("%player%", player.getName()));
                    return false;
                }
                plugin.getTeamManager().leaveAllTeams(player);
                sender.sendMessage(chatUtils.getMessage("commands.leave.self.successall"));
                configManager.saveTeamsToConfig();
            }
        } else {
            if (team == null) {
                sender.sendMessage(chatUtils.getMessage("error.commands.team_not_found")
                        .replace("%team%", teamName));
                return false;
            }

            if (args.length > 2) {
                String targetPlayerName = args[2];
                Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

                if (targetPlayer == null) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.player_not_found")
                            .replace("%player%", targetPlayer.getName()));
                    return false;
                }

                if (!plugin.getTeamManager().isInTeam(targetPlayer, teamName)) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.leave.other.not_in_team")
                            .replace("%player%", targetPlayer.getName())
                            .replace("%team%", teamName));
                    return false;
                }

                plugin.getTeamManager().leaveTeam(targetPlayer, teamName);
                sender.sendMessage(chatUtils.getMessage("commands.leave.other.success")
                        .replace("%player%", targetPlayer.getName())
                        .replace("%team%", teamName));
                configManager.saveTeamsToConfig();
            } else {
                if (!plugin.getTeamManager().isInTeam(player, teamName)) {
                    sender.sendMessage(chatUtils.getMessage("error.commands.leave.not_in_team")
                            .replace("%player%", player.getName())
                            .replace("%team%", teamName));
                    return false;
                }

                plugin.getTeamManager().leaveTeam(player, teamName);
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
        sender.sendMessage(ChatUtils.formatColor("&f                           xDrygo #707070» &7&o(@eldrygo)"));
        sender.sendMessage(ChatUtils.formatColor("&7"));
        sender.sendMessage(ChatUtils.formatColor("#fff18d&l                  ʀᴜɴɴɪɴɢ ᴘʟᴜɢɪɴ ᴠᴇʀꜱɪᴏɴ"));
        sender.sendMessage(ChatUtils.formatColor("&f                                    " + plugin.version));
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