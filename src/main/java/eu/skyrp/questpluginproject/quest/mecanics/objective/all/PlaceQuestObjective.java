package eu.skyrp.questpluginproject.quest.mecanics.objective.all;

import eu.skyrp.questpluginproject.quest.mecanics.objective.BaseCountableQuestObjective;
import lombok.Builder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.UUID;

public class PlaceQuestObjective extends BaseCountableQuestObjective<BlockPlaceEvent, Material> {

    @Builder
    public PlaceQuestObjective(String id, UUID player, Material blockType, int toReach) {
        super(id, player, blockType, toReach);
    }

    @Override
    @EventHandler
    public void onEventTriggered(BlockPlaceEvent event) {
        if (event.getBlockPlaced().getType() != super.target) {
            return;
        }

        super.incrementCount();

        if (super.getCount() < super.getAmount()) {
            return;
        }

        event.getPlayer().sendMessage("§a[Quests] Quête terminée !");
    }

}
