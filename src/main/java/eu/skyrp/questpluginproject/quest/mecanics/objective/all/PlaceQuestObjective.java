package eu.skyrp.questpluginproject.quest.mecanics.objective.all;

import eu.skyrp.questpluginproject.quest.mecanics.objective.BaseCountableQuestObjective;
import lombok.Builder;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.UUID;

public class PlaceQuestObjective extends BaseCountableQuestObjective<BlockPlaceEvent, Block> {

    @Builder
    public PlaceQuestObjective(String id, UUID player, Block target, int toReach) {
        super(id, player, target, toReach);
    }

    @Override
    @EventHandler
    public void onEventTriggered(BlockPlaceEvent event) {
        // TODO : A faire
    }
}
