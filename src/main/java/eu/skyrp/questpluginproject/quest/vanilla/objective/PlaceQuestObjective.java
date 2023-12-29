package eu.skyrp.questpluginproject.quest.vanilla.objective;

import eu.skyrp.questpluginproject.quest.common.types.MechanicType;
import lombok.Builder;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceQuestObjective extends BaseVanillaBlockQuestObjective<BlockPlaceEvent> {

    @Builder
    public PlaceQuestObjective(String id, String targetId, int amount) {
        super(BlockPlaceEvent.class, MechanicType.PLACE, id, targetId, amount);
    }

    public PlaceQuestObjective() {
        this(null, null, 0);
    }

    @Override
    protected Player getEventPlayer(BlockPlaceEvent event) {
        return event.getPlayer();
    }
}
