package eu.skyrp.questpluginproject.quest.mecanics.objective.cache;

import eu.skyrp.questpluginproject.quest.mecanics.objective.BaseCountableQuestObjective;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public abstract class BaseItemQuestObjective<T extends Event, U> extends BaseCountableQuestObjective<T, U> implements Cachable<ItemStack> {

    private final NamespacedKey playerItems;

    /**
     * Constructeur de la classe ItemStatisticQuestObjective.
     *
     * @param id         Id de la quête.
     * @param playerUUID UUID du joueur concerné par la quête.
     * @param target     Objet inclus dans l'objectif de quête.
     * @param amount     Nombre requis pour atteindre le bout de la quête.
     * @param plugin     Instance de la classe principale du plugin.
     */
    public BaseItemQuestObjective(String id, UUID playerUUID, U target, int amount, JavaPlugin plugin) {
        super(id, playerUUID, target, amount);
        this.playerItems = new NamespacedKey(plugin, super.playerUUID().toString());
    }

    @Override
    public boolean hasElemPlayer(Player player, ItemStack elem) {
        return elem.getItemMeta().getPersistentDataContainer().has(this.playerItems);
    }

    @Override
    public void addPlayerToElem(Player player, ItemStack elem) {
        ItemMeta meta = elem.getItemMeta();

        if (meta == null) {
            return;
        }

        meta.getPersistentDataContainer().set(this.playerItems, PersistentDataType.BYTE_ARRAY, elem.serializeAsBytes());
    }

//    private static ItemStack getItemById(String targetId) {
//        if (!targetId.contains(":")) {
//            return new ItemStack(Material.valueOf(targetId.toUpperCase()));
//        }
//
//        String[] parts = targetId.split(":");
//        String prefix = parts[0];
//        String item   = parts[1];
//
//        if (prefix.equalsIgnoreCase("ia") || prefix.equalsIgnoreCase("itemsadder")) {
//            return CustomBlock.getInstance(item).getItemStack();
//        } else {
//            throw new IllegalArgumentException("The \"" + prefix + "\" item is not recognized by the plugin. " +
//                    "Please use \"ia:\"/\"itemsadder:\" prefix for ItemsAdder.");
//        }
//    }
}
