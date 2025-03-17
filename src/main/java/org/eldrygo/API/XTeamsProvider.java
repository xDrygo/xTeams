package org.eldrygo.API;

import org.eldrygo.XTeams;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class XTeamsProvider {

    public static XTeamsAPI getAPI() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("xTeams");
        if (plugin instanceof XTeams xTeams) {
            return new XTeamsAPI(xTeams);
        }
        throw new IllegalStateException("XTeams plugin not found or not enabled.");
    }
}
