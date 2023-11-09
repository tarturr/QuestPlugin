package eu.skyrp.questpluginproject.quest.mecanics.objective.all;

import dev.lone.itemsadder.api.Events.CustomBlockPlaceEvent;
import eu.skyrp.questpluginproject.quest.mecanics.objective.cache.BaseBlockQuestObjective;
import lombok.Builder;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class PlaceQuestObjective extends BaseBlockQuestObjective<BlockPlaceEvent> {

    @Builder
    public PlaceQuestObjective(String id, UUID player, String targetId, int toReach, JavaPlugin plugin) {
        super(id, player, targetId, toReach, plugin);
    }

    @Override
    @EventHandler
    public void onEventTriggered(BlockPlaceEvent event) {
        super.onEventTriggered(event);
    }

    @EventHandler
    public void onCustomBlockPlace(CustomBlockPlaceEvent event) {
        final Player player = event.getPlayer();
        final Block block = event.getBlock();

        this.onBlockTriggered(block, player);
    }

    @Override
    protected Player getEventPlayer(BlockPlaceEvent event) {
        return event.getPlayer();
    }
}
