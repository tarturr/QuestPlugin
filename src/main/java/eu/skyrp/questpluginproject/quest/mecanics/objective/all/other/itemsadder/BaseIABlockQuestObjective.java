package eu.skyrp.questpluginproject.quest.mecanics.objective.all.other.itemsadder;

import dev.lone.itemsadder.api.CustomBlock;
import eu.skyrp.questpluginproject.quest.mecanics.objective.all.other.CustomItemStackFinder;
import eu.skyrp.questpluginproject.quest.mecanics.objective.cache.BaseBlockQuestObjective;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public abstract class BaseIABlockQuestObjective<T extends PlayerEvent> extends BaseBlockQuestObjective<T, ItemStack> {
    /**
     * @param id         Id de la qu�te.
     * @param playerUUID UUID du joueur concern� par la qu�te.
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de qu�te.
     * @param amount     Nombre requis pour atteindre le bout de la qu�te.
     * @param plugin     Instance de la classe principale du plugin.
     */
    public BaseIABlockQuestObjective(String id, UUID playerUUID, String targetId, int amount, JavaPlugin plugin) {
        super(
                id,
                playerUUID,
                CustomItemStackFinder.getItemById(
                        targetId,
                        "ItemsAdder",
                        "ia",
                        CustomBlock::isInRegistry,
                        (item) -> CustomBlock.getInstance(item).getItemStack()
                ),
                amount,
                plugin
        );
    }
}
