package eu.skyrp.questpluginproject.quest.mecanics.objective.all.other.itemsadder;

import dev.lone.itemsadder.api.CustomBlock;
import eu.skyrp.questpluginproject.quest.mecanics.objective.all.other.CustomItemNotFoundException;
import eu.skyrp.questpluginproject.quest.mecanics.objective.all.other.MalformedCustomItemIDException;
import eu.skyrp.questpluginproject.quest.mecanics.objective.cache.BaseBlockQuestObjective;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public abstract class BaseIABlockQuestObjective<T extends PlayerEvent> extends BaseBlockQuestObjective<T, ItemStack> {
    /**
     * @param id         Id de la quête.
     * @param playerUUID UUID du joueur concerné par la quête.
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de quête.
     * @param amount     Nombre requis pour atteindre le bout de la quête.
     * @param plugin     Instance de la classe principale du plugin.
     */
    public BaseIABlockQuestObjective(String id, UUID playerUUID, String targetId, int amount, JavaPlugin plugin) {
        super(id, playerUUID, getIABlockById(targetId), amount, plugin);
    }

    private static ItemStack getIABlockById(String targetId) {
        if (!targetId.contains(":")) {
            throw new MalformedCustomItemIDException("ItemsAdder", targetId, "ia");
        }

        String[] parts  = targetId.split(":");
        String prefix   = parts[0];
        String item     = parts[1];

        if (!(prefix.equalsIgnoreCase("ia") || prefix.equalsIgnoreCase("itemsadder"))) {
            throw new MalformedCustomItemIDException("ItemsAdder", targetId, "ia");
        }

        if (!CustomBlock.isInRegistry(item)) {
            throw new CustomItemNotFoundException("ItemsAdder", targetId);
        }

        return CustomBlock.getInstance(item).getItemStack();
    }
}
