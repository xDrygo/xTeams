package org.eldrygo.Models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Team {
    public final Map<String, Team> teams = new HashMap<>();

    private final String name;
    private final String displayName;
    private final Set<String> members = new HashSet<>();

    public Team(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Set<String> getMembers() {
        return members;
    }

    public void addMember(String player) {
        members.add(player);
    }

    public void removeMember(String player) {
        members.remove(player);
    }
}
