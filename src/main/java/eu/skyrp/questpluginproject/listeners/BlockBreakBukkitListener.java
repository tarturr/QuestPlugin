package eu.skyrp.questpluginproject.listeners;

import dev.lone.itemsadder.api.CustomBlock;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakBukkitListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (event.isCancelled()) {
            return;
        }

        if (CustomBlock.byAlreadyPlaced(block) == null) {
            Bukkit.getPluginManager().callEvent(
                    new eu.skyrp.questpluginproject.api.events.BlockBreakEvent(block, event.getPlayer())
            );
        }
    }

}
