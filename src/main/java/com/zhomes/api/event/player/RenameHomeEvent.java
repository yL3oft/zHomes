package com.zhomes.api.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class RenameHomeEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();

    private boolean isCancelled;

    protected String home;
    protected String newName;

    public RenameHomeEvent(Player who, String home, String newName) {
        super(who);
        this.home = home;
        this.newName = newName;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public String getHome() {
        return this.home;
    }
    public String getNewName() {
        return this.newName;
    }

    public void setHome(String home) {
        this.home = home;
    }
    public void setNewName(String newName) {
        this.newName = newName;
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
