package org.eldrygo.Models;

import org.bukkit.OfflinePlayer;
import org.eldrygo.XTeams;

import java.util.HashSet;
import java.util.Set;

public class Team {

    private final XTeams plugin;
    private final String name;
    private final String displayName;
    private final int priority;
    private final Set<String> members;

    public Team(XTeams plugin, String name, String displayName, int priority, Set<String> members) {
        this.plugin = plugin;
        this.name = name;
        this.displayName = displayName;
        this.priority = priority;
        this.members = members != null ? members : new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }
    public int getPriority() { return priority; }
    public Set<String> getMembers() {
        return members;
    }

    public void addMember(String playerName) {
        members.add(playerName);
    }

    public void removeMember(String playerName) {
        members.remove(playerName);
    }

    public boolean hasMember(OfflinePlayer player) {
        return members.contains(player.getName());
    }
}