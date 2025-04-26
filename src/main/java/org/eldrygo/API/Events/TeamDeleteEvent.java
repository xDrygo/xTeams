package org.eldrygo.API.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TeamDeleteEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final String teamName;

    public TeamDeleteEvent(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}