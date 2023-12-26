package eu.skyrp.questpluginproject.quest.custom.slimefun.objective;

import eu.skyrp.questpluginproject.quest.common.types.ObjectiveType;
import eu.skyrp.questpluginproject.quest.custom.common.CustomObjectFinder;
import eu.skyrp.questpluginproject.quest.common.objective.cache.BaseBlockQuestObjective;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import org.bukkit.event.Event;

public abstract class BaseSFBlockQuestObjective<T extends Event> extends BaseBlockQuestObjective<T, SlimefunItem>  {
    /**
     * @param id         Id de la qu�te.
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de qu�te.
     * @param amount     Nombre requis pour atteindre le bout de la qu�te.
     */
    public BaseSFBlockQuestObjective(Class<T> eventType, ObjectiveType type, String id, String targetId, int amount) {
        super(
                eventType,
                type,
                id,
                CustomObjectFinder.getById(
                        targetId,
                        "SlimeFun",
                        "sf",
                        (item) -> SlimefunItem.getById(item) == null,
                        SlimefunItem::getById
                ),
                amount
        );
    }
}
