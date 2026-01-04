package dev.drygo.XTeams.API.Events;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class TeamLeaveEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final OfflinePlayer player;
    private final String teamName;

    public TeamLeaveEvent(OfflinePlayer player, String teamName) {
        this.player = player;
        this.teamName = teamName;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public String getTeamName() {
        return teamName;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
