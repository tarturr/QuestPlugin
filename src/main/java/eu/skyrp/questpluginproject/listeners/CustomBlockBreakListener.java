package eu.skyrp.questpluginproject.listeners;

import dev.lone.itemsadder.api.Events.CustomBlockBreakEvent;
import eu.skyrp.questpluginproject.api.events.BlockBreakEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CustomBlockBreakListener implements Listener {

    @EventHandler
    public void onCustomBlockBreak(CustomBlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Bukkit.getPluginManager().callEvent(
                new BlockBreakEvent(event.getBlock(), event.getPlayer())
        );
    }

}
