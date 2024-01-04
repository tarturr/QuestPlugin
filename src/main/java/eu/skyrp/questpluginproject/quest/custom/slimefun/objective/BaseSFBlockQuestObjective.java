package eu.skyrp.questpluginproject.quest.custom.slimefun.objective;

import eu.skyrp.questpluginproject.quest.common.types.MechanicType;
import eu.skyrp.questpluginproject.quest.custom.common.CustomObjectFinder;
import eu.skyrp.questpluginproject.quest.common.objective.cache.BaseBlockQuestObjective;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import org.bukkit.event.Event;

public abstract class BaseSFBlockQuestObjective<T extends Event> extends BaseBlockQuestObjective<T, SlimefunItem>  {
    /**
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de quête.
     * @param amount     Nombre requis pour atteindre le bout de la quête.
     */
    public BaseSFBlockQuestObjective(Class<T> eventType, MechanicType type, String targetId, int amount) {
        super(eventType, type, targetId == null ? null : blockFromString(targetId), amount);
    }

    private static SlimefunItem blockFromString(String targetId) {
        return CustomObjectFinder.getById(
                targetId,
                "SlimeFun",
                "sf",
                (item) -> SlimefunItem.getById(item) == null,
                SlimefunItem::getById
        );
    }
}
