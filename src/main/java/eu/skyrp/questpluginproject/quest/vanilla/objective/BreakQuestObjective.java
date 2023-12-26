package eu.skyrp.questpluginproject.quest.vanilla.objective;

import eu.skyrp.questpluginproject.quest.common.types.ObjectiveType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakQuestObjective extends BaseVanillaBlockQuestObjective<BlockBreakEvent> {
    /**
     * @param id         Id de la quête.
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de quête.
     * @param amount     Nombre requis pour atteindre le bout de la quête.
     */
    public BreakQuestObjective(String id, String targetId, int amount) {
        super(BlockBreakEvent.class, ObjectiveType.BREAK, id, targetId, amount);
    }

    @Override
    protected Player getEventPlayer(BlockBreakEvent event) {
        return event.getPlayer();
    }
}
