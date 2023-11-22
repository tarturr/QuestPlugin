package eu.skyrp.questpluginproject.quest.mecanics.objective.all.vanilla;

import lombok.Builder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class PlaceQuestObjective extends BaseVanillaBlockQuestObjective<BlockPlaceEvent> {

    @Builder
    public PlaceQuestObjective(String id, UUID playerUUID, String targetId, int amount, JavaPlugin plugin) {
        super(id, playerUUID, targetId, amount, plugin);
    }

    @Override
    @EventHandler
    public void onEventTriggered(BlockPlaceEvent event) {
        super.onEventTriggered(event);
    }

    @Override
    protected Player getEventPlayer(BlockPlaceEvent event) {
        return event.getPlayer();
    }
}
