package eu.skyrp.questpluginproject.quest.vanilla.objective;

import eu.skyrp.questpluginproject.quest.common.types.ObjectiveType;
import lombok.Builder;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceQuestObjective extends BaseVanillaBlockQuestObjective<BlockPlaceEvent> {

    @Builder
    public PlaceQuestObjective(String id, String targetId, int amount) {
        super(BlockPlaceEvent.class, ObjectiveType.PLACE, id, targetId, amount);
    }

    @Override
    protected Player getEventPlayer(BlockPlaceEvent event) {
        return event.getPlayer();
    }
}
