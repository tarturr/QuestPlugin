package eu.skyrp.questpluginproject.quest.mecanics.objective.all.other.slimefun;

import eu.skyrp.questpluginproject.quest.mecanics.objective.all.other.CustomItemStackFinder;
import eu.skyrp.questpluginproject.quest.mecanics.objective.cache.BaseBlockQuestObjective;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public abstract class BaseSFBlockQuestObjective<T extends BlockEvent> extends BaseBlockQuestObjective<T, ItemStack>  {
    /**
     * @param id         Id de la quête.
     * @param playerUUID UUID du joueur concerné par la quête.
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de quête.
     * @param amount     Nombre requis pour atteindre le bout de la quête.
     * @param plugin     Instance de la classe principale du plugin.
     */
    public BaseSFBlockQuestObjective(String id, UUID playerUUID, String targetId, int amount, JavaPlugin plugin) {
        super(
                id,
                playerUUID,
                CustomItemStackFinder.getItemById(
                        targetId,
                        "SlimeFun",
                        "sf",
                        (item) -> SlimefunItem.getById(item) == null,
                        (item) -> SlimefunItem.getById(item).getItem()
                ),
                amount,
                plugin
        );
    }
}
