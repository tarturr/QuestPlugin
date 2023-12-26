package eu.skyrp.questpluginproject.quest.common.objective.cache;

import eu.skyrp.questpluginproject.quest.common.objective.BaseQuestObjective;
import eu.skyrp.questpluginproject.quest.common.types.ObjectiveType;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public abstract class BaseItemQuestObjective<T extends Event, U> extends BaseQuestObjective<T, U> implements Cachable<ItemStack> {


    private NamespacedKey playerItems;

    /**
     * Constructeur de la classe ItemStatisticQuestObjective.
     *
     * @param id         Id de la qu�te.
     * @param target     Objet inclus dans l'objectif de qu�te.
     * @param amount     Nombre requis pour atteindre le bout de la qu�te.
     */
    public BaseItemQuestObjective(Class<T> eventType, ObjectiveType type, String id, U target, int amount) {
        super(eventType, type, id, target, amount);
    }

    @Override
    public boolean hasElemPlayer(Player player, ItemStack elem) {
        return this.playerItems != null && elem.getItemMeta().getPersistentDataContainer().has(this.playerItems);
    }

    @Override
    public void addPlayerToElem(Player player, ItemStack elem) {
        this.playerItems = new NamespacedKey("ItemQuestObjective", player.getUniqueId().toString());

        ItemMeta meta = elem.getItemMeta();

        if (meta == null) {
            return;
        }

        meta.getPersistentDataContainer().set(this.playerItems, PersistentDataType.BYTE_ARRAY, elem.serializeAsBytes());
    }
}
