package org.eldrygo.Managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.eldrygo.Models.Team;
import org.eldrygo.XTeams;
import org.eldrygo.Utils.ChatUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConfigManager {

    private final XTeams plugin;

    public ConfigManager(XTeams plugin) {
        this.plugin = plugin;
    }

    public String getPrefix() {
        return plugin.prefix;
    }

    public FileConfiguration getMessageConfig() {
        return plugin.messagesConfig;
    }

    public void loadConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();
    }

    public void loadMessages() {
        try {
            File messagesFile = new File(plugin.getDataFolder(), "messages.yml");
            if (!messagesFile.exists()) {
                plugin.saveResource("messages.yml", false);
                plugin.getLogger().info("✅ The messages.yml file did not exist, it has been created.");
            } else {
                plugin.getLogger().info("✅ The messages.yml file has been loaded successfully.");
            }
            plugin.messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
            plugin.prefix = ChatUtils.formatColor(getMessageConfig().getString("prefix", "#ffbaff&lx&r&lTeams &cDefault Prefix &8»&r"));
        } catch (Exception e) {
            plugin.getLogger().severe("❌ Failed to load messages configuration!");
            e.printStackTrace();
        }
    }

    public void loadTeamsFromConfig() {
        FileConfiguration config = plugin.getConfig();

        if (config.contains("teams")) {
            for (String teamName : config.getConfigurationSection("teams").getKeys(false)) {
                List<String> players = config.getStringList("teams." + teamName + ".members");
                String displayName = config.getString("teams." + teamName + ".displayName", teamName);
                int priority = config.getInt("teams." + teamName + ".priority", 0);  // Si no hay displayName, usar el teamName

                // Crear el equipo con los jugadores y el displayName directamente desde la configuración
                Set<String> playerSet = new HashSet<>(players); // Convertir la lista de jugadores en un Set
                Team team = new Team(plugin, teamName, displayName, priority, playerSet);  // Usar el constructor adecuado
                plugin.getTeamManager().addTeam(team);  // CORRECCIÓN: Añadir el equipo con el nombre

                plugin.getLogger().info("Team loaded: " + teamName);
            }
        }
    }

    public void saveTeamsToConfig() {
        FileConfiguration config = plugin.getConfig();
        // Limpiar la sección de equipos para evitar duplicados
        config.set("teams", null);
        // Guardar cada equipo directamente en la configuración
        for (Team team : plugin.getTeamManager().getAllTeams()) { // Iteramos sobre los objetos Team
            if (team == null) {
                plugin.getLogger().warning("A null team was encountered, skipping save.");
                continue; // Si el equipo es null, lo saltamos
            }
            String teamName = team.getName(); // Obtener el nombre del equipo
            Set<String> members = team.getMembers(); // Obtener los miembros del equipo

            // Convertir el Set en una lista para que sea compatible con YAML
            List<String> memberList = new ArrayList<>(members);

            // Guardar los jugadores de ese equipo en la configuración
            config.set("teams." + teamName + ".members", memberList); // Usamos el teamName que es un String
            config.set("teams." + teamName + ".displayName", team.getDisplayName()); // Usamos el displayName que es un String
            config.set("teams." + teamName + ".priority", team.getPriority()); // Usamos el priority que es un integer
        }
        // Guardar la configuración en el archivo
        plugin.saveConfig();
    }
}
