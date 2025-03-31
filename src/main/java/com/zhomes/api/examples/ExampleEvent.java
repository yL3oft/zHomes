package com.zhomes.api.examples;

import com.zhomes.api.event.player.ExecuteSethomeCommandEvent;
import com.zhomes.api.event.player.PreExecuteSethomeCommandEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ExampleEvent implements Listener {

    @EventHandler
    public void onPreExecuteSethomeCommandEvent(PreExecuteSethomeCommandEvent event) {
        Player player = event.getPlayer();
    }

    @EventHandler
    public void onExecuteSethomeCommandEvent(ExecuteSethomeCommandEvent event) {
        Player player = event.getPlayer();
        String home = event.getHome();
        event.setHome("test");
    }

}
