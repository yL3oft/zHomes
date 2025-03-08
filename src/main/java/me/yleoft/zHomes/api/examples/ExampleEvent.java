package me.yleoft.zHomes.api.examples;

import me.yleoft.zHomes.api.events.ExecuteSethomeCommandEvent;
import me.yleoft.zHomes.api.events.PreExecuteSethomeCommandEvent;
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
