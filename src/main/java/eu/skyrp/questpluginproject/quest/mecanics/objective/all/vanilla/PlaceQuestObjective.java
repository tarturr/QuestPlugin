package eu.skyrp.questpluginproject.quest.mecanics.objective.all.vanilla;

import lombok.Builder;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class PlaceQuestObjective extends BaseVanillaBlockQuestObjective<BlockPlaceEvent> {

    @Builder
    public PlaceQuestObjective(String id, UUID player, String targetId, int toReach, JavaPlugin plugin) {
        super(id, player, targetId, toReach, plugin);
    }

    @Override
    protected Player getEventPlayer(BlockPlaceEvent event) {
        return event.getPlayer();
    }
}
