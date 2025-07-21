package dev.drygo.XTeams.Hooks.LuckPerms.Managers;

import dev.drygo.XTeams.XTeams;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class LuckPermsGroupManager {
    private final XTeams plugin;
    private final LuckPerms luckPerms;
    private final Map<String, String> teamGroupMap = new HashMap<>();

    public LuckPermsGroupManager(XTeams plugin) {
        this.plugin = plugin;
        this.luckPerms = LuckPermsProvider.get();
        loadFromConfig();
    }

    private void loadFromConfig() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("hooks.luckperms.team_groups");
        if (section != null) {
            for (String team : section.getKeys(false)) {
                String group = section.getString(team);
                if (group != null && !group.isBlank()) {
                    teamGroupMap.put(team.toLowerCase(), group);
                }
            }
        }
    }

    public void applyGroup(Player player, String teamName) {
        handleGroupChange(player, teamName, true);
    }

    public void removeGroup(Player player, String teamName) {
        handleGroupChange(player, teamName, false);
    }

    private void handleGroupChange(Player player, String teamName, boolean add) {
        String teamKey = teamName.toLowerCase();
        String group = teamGroupMap.get(teamKey);

        if (group == null || group.isBlank()) {
            plugin.getLogger().warning("❌ No LuckPerms group found for team: " + teamName);
            return;
        }

        User user = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (user == null) {
            plugin.getLogger().warning("❌ User not found for player: " + player.getName());
            return;
        }

        InheritanceNode node = InheritanceNode.builder(group).build();

        if (add) {
            user.data().add(node);
        } else {
            user.data().remove(node);
        }

        luckPerms.getUserManager().saveUser(user);
    }
}