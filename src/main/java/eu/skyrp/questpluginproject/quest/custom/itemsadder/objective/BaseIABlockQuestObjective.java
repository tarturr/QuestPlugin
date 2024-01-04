package eu.skyrp.questpluginproject.quest.custom.itemsadder.objective;

import dev.lone.itemsadder.api.CustomBlock;
import eu.skyrp.questpluginproject.quest.common.types.MechanicType;
import eu.skyrp.questpluginproject.quest.custom.common.CustomObjectFinder;
import eu.skyrp.questpluginproject.quest.common.objective.cache.BaseBlockQuestObjective;
import org.bukkit.event.player.PlayerEvent;

public abstract class BaseIABlockQuestObjective<T extends PlayerEvent> extends BaseBlockQuestObjective<T, CustomBlock> {
    /**
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de quête.
     * @param amount     Nombre requis pour atteindre le bout de la quête.
     */
    public BaseIABlockQuestObjective(Class<T> eventType, MechanicType type, String targetId, int amount) {
        super(eventType, type, targetId == null ? null : blockFromString(targetId), amount);
    }

    private static CustomBlock blockFromString(String targetId) {
        return CustomObjectFinder.getById(
                targetId,
                "ItemsAdder",
                "ia",
                CustomBlock::isInRegistry,
                CustomBlock::getInstance
        );
    }
}
