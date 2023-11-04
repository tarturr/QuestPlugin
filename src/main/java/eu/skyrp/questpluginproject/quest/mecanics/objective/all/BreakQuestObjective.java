package eu.skyrp.questpluginproject.quest.mecanics.objective.all;

import eu.skyrp.questpluginproject.quest.mecanics.objective.BaseCountableQuestObjective;
import lombok.Builder;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.UUID;

public class BreakQuestObjective extends BaseCountableQuestObjective<BlockBreakEvent, Block> {

    @Builder
    public BreakQuestObjective(String id, UUID player, Block target, int amount) {
        super(id, player, target, amount);
    }

    @Override
    @EventHandler
    public void onEventTriggered(BlockBreakEvent event) {
        // TODO : A faire
    }
}
