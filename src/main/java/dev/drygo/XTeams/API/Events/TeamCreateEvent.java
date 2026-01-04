package dev.drygo.XTeams.API.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import dev.drygo.XTeams.Models.Team;
import org.jetbrains.annotations.NotNull;

public class TeamCreateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Team team;

    public TeamCreateEvent(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}