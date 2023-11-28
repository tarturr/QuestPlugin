package eu.skyrp.questpluginproject.quest.custom.itemsadder.objective;

import dev.lone.itemsadder.api.CustomBlock;
import eu.skyrp.questpluginproject.quest.custom.common.CustomObjectFinder;
import eu.skyrp.questpluginproject.quest.common.objective.cache.BaseBlockQuestObjective;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public abstract class BaseIABlockQuestObjective<T extends PlayerEvent> extends BaseBlockQuestObjective<T, CustomBlock> {
    /**
     * @param id         Id de la quête.
     * @param playerUUID UUID du joueur concerné par la quête.
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de quête.
     * @param amount     Nombre requis pour atteindre le bout de la quête.
     * @param plugin     Instance de la classe principale du plugin.
     */
    public BaseIABlockQuestObjective(Class<T> eventType, String id, UUID playerUUID, String targetId, int amount, JavaPlugin plugin) {
        super(
                eventType,
                id,
                playerUUID,
                CustomObjectFinder.getById(
                        targetId,
                        "ItemsAdder",
                        "ia",
                        CustomBlock::isInRegistry,
                        CustomBlock::getInstance
                ),
                amount,
                plugin
        );
    }
}
