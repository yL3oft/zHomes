package me.leonardo.zhomes.api.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class TeleportToHomeEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled;
    protected Location from;
    protected Location to;
    protected boolean isDimensionalTeleport;
    protected String home;

    public TeleportToHomeEvent(final Player who, final String home, Location from, Location to, boolean isDimensionalTeleport) {
        super(who);
        this.home = home;
        this.from = from;
        this.to = to;
        this.isDimensionalTeleport = isDimensionalTeleport;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Location getFrom() {
        return this.from;
    }

    public Location getTo() {
        return this.to;
    }

    public boolean isDimensionalTeleport() {
        return this.isDimensionalTeleport;
    }

    public String getHome() {
        return this.home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

}
