package org.eldrygo.XTeams.Models;

import org.bukkit.OfflinePlayer;

import java.util.HashSet;
import java.util.Set;

public class Team {
    private final String name;
    private String displayName;
    private final int priority;
    private final Set<String> members;

    public Team(String name, String displayName, int priority, Set<String> members) {
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
    public void setDisplayName(String displayName) { this.displayName = displayName; }
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

    public boolean hasMember(String playerName) {
        return members.contains(playerName);
    }
}