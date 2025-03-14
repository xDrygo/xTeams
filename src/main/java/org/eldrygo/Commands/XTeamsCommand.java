package org.eldrygo.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.eldrygo.Utils.ChatUtils;
import org.eldrygo.XTeams;

import java.util.List;
import java.util.Map;

public class XTeamsCommand implements CommandExecutor {
    private final XTeams plugin;
    private ChatUtils chatUtils;

    public XTeamsCommand(XTeams plugin) {
        this.plugin = plugin;
        this.chatUtils = plugin.getChatUtils();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(chatUtils.getMessage("unknown_command"));
            return false;
        }

        String subcommand = args[0].toLowerCase();

        // Llamar a los subcomandos
        switch (subcommand) {
            case "create":
                return handleCreate(sender, args);
            case "delete":
                return handleDelete(sender, args);
            case "list":
                return handleList(sender);
            case "join":
                return handleJoin(sender, args);
            case "info":
                return handleInfo(sender, args);
            case "leave":
                return handleLeave(sender, args);
            default:
                sender.sendMessage(chatUtils.getMessage("commands.error.unknown_command"));
                return false;
        }
    }

    private boolean handleCreate(CommandSender sender, String[] args) {

        if (args.length < 2) {
            sender.sendMessage(chatUtils.getMessage("error.commands.team_not_specified.create"));
            return false;
        }

        String teamName = args[1];
        // Lógica para crear el equipo (puedes agregar más validaciones aquí)
        plugin.getTeamManager().createTeam(teamName);
        sender.sendMessage(chatUtils.getMessage("commands.create.success")
                .replace("%team%", teamName));
        return true;
    }

    private boolean handleDelete(CommandSender sender, String[] args) {

        if (args.length < 2) {
            sender.sendMessage(chatUtils.getMessage("error.commands.team_not_specified.delete"));
            return false;
        }

        String teamName = args[1];
        // Lógica para eliminar el equipo
        if (teamName.equals("*")) {
            plugin.getTeamManager().deleteAllTeams();
            sender.sendMessage(chatUtils.getMessage("commands.delete.successall"));
        } else {
            plugin.getTeamManager().deleteTeam(teamName);
            sender.sendMessage(chatUtils.getMessage("commands.delete.success")
                    .replace("%team%", teamName));
        }
        return true;
    }

    private boolean handleList(CommandSender sender) {
        // Obtener la lista de equipos
        String teams = plugin.getTeamManager().listTeams();
        if (teams == null || teams.isEmpty()) {
            sender.sendMessage(chatUtils.getMessage("commands.list.empty"));
        } else {

        StringBuilder message = new StringBuilder();

        String header = chatUtils.getMessage("commands.list.string.header");
        message.append(header).append("\n");

        for (String team : teams.split(", ")) {
            message.append(chatUtils.getMessage("commands.list.string.row")
                .replace("%team%", team)).append("\n");

        }
        sender.sendMessage(message.toString());
        return true;
        }
        return false;
    }

    private boolean handleJoin(CommandSender sender, String[] args) {

        if (args.length < 2) {
            sender.sendMessage(chatUtils.getMessage("error.commands.team_not_specified.join"));
            return false;
        }
        if (args.length == 2) {
            String teamName = args[1];
            Player player = (Player) sender;
            if (!(sender instanceof Player)) {
                sender.sendMessage(chatUtils.getMessage("error.commands.join.self_onlyplayer"));
                return false;
            } else {
                if (teamName.equals("*")) {
                    plugin.getTeamManager().joinAllTeams(player);
                    sender.sendMessage(chatUtils.getMessage("commands.join.self.successall"));
                } else {
                    plugin.getTeamManager().joinTeam(player, teamName);
                    sender.sendMessage(chatUtils.getMessage("commands.join.self.success")
                            .replace("%team%", teamName));
                }
            }
        }

        if (args.length == 3) {
            String teamName = args[1];
            String target = args[2];
            Player targetPlayer = plugin.getServer().getPlayer(target);
            if (!plugin.getServer().getPlayer(target).isOnline()) {
                sender.sendMessage(chatUtils.getMessage("error.commands.join.player_not_found")
                        .replace("%player%", target));
                return false;
            } else {
                if (teamName.equals("*")) {
                    plugin.getTeamManager().joinAllTeams(targetPlayer);
                    sender.sendMessage(chatUtils.getMessage("commands.leave.join.successall")
                            .replace("%player%", target));
                } else {
                    plugin.getTeamManager().joinTeam(targetPlayer, teamName);
                    sender.sendMessage(chatUtils.getMessage("commands.leave.join.success")
                            .replace("%team%", teamName)
                            .replace("%player%", target));
                }
            }
        }
        return true;
    }

    public boolean handleInfo(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(chatUtils.getMessage("error.commands.team_not_specified.info"));
            return false;
        }
        String teamName = args[1];  // Aquí extraes el teamName del array args
        // Obtener la información del equipo usando el TeamManager
        Map<String, Object> teamInfo = plugin.getTeamManager().getTeamInfo(new String[]{teamName});
        if (teamInfo == null) {
            sender.sendMessage(chatUtils.getMessage("error.commands.info.team_not_found"));
            return false;
        }

        // Obtener la información del equipo
        String displayName = (String) teamInfo.get("displayName");
        String name = (String) teamInfo.get("name");
        List<String> members = (List<String>) teamInfo.get("members");

        // Crear el mensaje en varias líneas usando getMessage()
        StringBuilder message = new StringBuilder();

        // Agregar el header (múltiples líneas)
        message.append(chatUtils.getMessage("commands.info.string.divisor"));

        List<String> headerLines = chatUtils.getMessageList("commands.info.string.header");
        for (String line : headerLines) {
            message.append(line.replace("%displayName%", displayName)
                            .replace("%teamName%", name))
                    .append("\n");
        }

        // Miembros
        if (members.isEmpty()) {
            message.append(chatUtils.getMessage("commands.info.string.no_members"));
        } else {
            message.append(chatUtils.getMessage("commands.info.string.members_header")).append("\n");
            for (String member : members) {
                message.append(chatUtils.getMessage("commands.info.string.member_row")
                        .replace("%member%", member)).append("\n");
            }
        }

        // Agregar el footer (múltiples líneas)
        List<String> footerLines = chatUtils.getMessageList("commands.info.string.footer");
        for (String line : footerLines) {
            message.append(line).append("\n");
        }

        message.append(chatUtils.getMessage("commands.info.string.divisor"));

        // Enviar el mensaje completo al jugador
        sender.sendMessage(message.toString());
        return true;
    }

    private boolean handleLeave(CommandSender sender, String[] args) {

        if (args.length < 2) {
            sender.sendMessage(chatUtils.getMessage("error.commands.team_not_specified.leave"));
            return false;
        }
        if (args.length == 2) {
            String teamName = args[1];
            Player player = (Player) sender;
            if (!(sender instanceof Player)) {
                sender.sendMessage(chatUtils.getMessage("error.commands.leave.self_onlyplayer"));
                return false;
            } else {
                if (teamName.equals("*")) {
                    plugin.getTeamManager().leaveAllTeams(player);
                    sender.sendMessage(chatUtils.getMessage("commands.leave.self.successall"));
                } else {
                    plugin.getTeamManager().leaveTeam(player, teamName);
                    sender.sendMessage(chatUtils.getMessage("commands.leave.self.success")
                            .replace("%team%", teamName));
                }
            }
        }

        if (args.length == 3) {
            String teamName = args[1];
            String target = args[2];
            Player targetPlayer = plugin.getServer().getPlayer(target);
            if (!plugin.getServer().getPlayer(target).isOnline()) {
                sender.sendMessage(chatUtils.getMessage("error.commands.leave.player_not_found")
                        .replace("%player%", target));
                return false;
            } else {
                if (teamName.equals("*")) {
                    plugin.getTeamManager().leaveAllTeams(targetPlayer);
                    sender.sendMessage(chatUtils.getMessage("commands.leave.other.successall")
                            .replace("%player%", target));
                } else {
                    plugin.getTeamManager().leaveTeam(targetPlayer, teamName);
                    sender.sendMessage(chatUtils.getMessage("commands.leave.other.success")
                            .replace("%team%", teamName)
                            .replace("%player%", target));
                }
            }
        }
        return true;
    }
}
