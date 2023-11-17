package eu.skyrp.questpluginproject.listeners;

import dev.lone.itemsadder.api.CustomBlock;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceBukkitListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();

        if (event.isCancelled()) {
            return;
        }

        if (CustomBlock.byAlreadyPlaced(block) == null) {
            Bukkit.getPluginManager().callEvent(
                    new eu.skyrp.questpluginproject.api.events.BlockPlaceEvent(block, event.getPlayer())
            );
        }
    }

}
