package eu.skyrp.questpluginproject.listeners;

import dev.lone.itemsadder.api.Events.CustomBlockPlaceEvent;
import eu.skyrp.questpluginproject.api.events.BlockPlaceEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CustomBlockPlaceListener implements Listener {

    @EventHandler
    public void onCustomBlockPlace(CustomBlockPlaceEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Bukkit.getPluginManager().callEvent(
                new BlockPlaceEvent(event.getBlock(), event.getPlayer())
        );
    }

}
