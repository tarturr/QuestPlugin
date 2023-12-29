package eu.skyrp.questpluginproject.quest.vanilla.objective;

import eu.skyrp.questpluginproject.quest.common.types.MechanicType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakQuestObjective extends BaseVanillaBlockQuestObjective<BlockBreakEvent> {

    public BreakQuestObjective() {
        this(null, null, 0);
    }

    /**
     * @param id         Id de la qu�te.
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de qu�te.
     * @param amount     Nombre requis pour atteindre le bout de la qu�te.
     */
    public BreakQuestObjective(String id, String targetId, int amount) {
        super(BlockBreakEvent.class, MechanicType.BREAK, id, targetId, amount);
    }

    @Override
    protected Player getEventPlayer(BlockBreakEvent event) {
        return event.getPlayer();
    }
}
