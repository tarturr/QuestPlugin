package eu.skyrp.questpluginproject.quest.vanilla.objective;

import eu.skyrp.questpluginproject.quest.common.types.MechanicType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakQuestObjective extends BaseVanillaBlockQuestObjective<BlockBreakEvent> {

    public BreakQuestObjective() {
        this(null, 0);
    }

    /**
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de quête.
     * @param amount     Nombre requis pour atteindre le bout de la quête.
     */
    public BreakQuestObjective(String targetId, int amount) {
        super(BlockBreakEvent.class, MechanicType.BREAK, targetId, amount);
    }

    @Override
    protected Player getEventPlayer(BlockBreakEvent event) {
        return event.getPlayer();
    }
}
