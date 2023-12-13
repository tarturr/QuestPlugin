package eu.skyrp.questpluginproject.quest.vanilla.objective;

import eu.skyrp.questpluginproject.quest.common.objective.cache.BaseItemQuestObjective;
import lombok.Builder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CollectQuestObjective extends BaseItemQuestObjective<EntityPickupItemEvent, Material> {

    /**
     * Constructeur de la classe ItemStatisticQuestObjective.
     *
     * @param id         Id de la qu�te.
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de qu�te.
     * @param amount     Nombre requis pour atteindre le bout de la qu�te.
     * @param plugin     Instance de la classe principale du plugin.
     */
    @Builder
    public CollectQuestObjective(String id, String targetId, int amount, JavaPlugin plugin) {
        super(EntityPickupItemEvent.class, id, Material.valueOf(targetId.toUpperCase()), amount, plugin);

        if (super.target() == null) {
            throw new IllegalArgumentException("The \"" + targetId + "\" item type could not be recognized.");
        }
    }

    /**
     * R�agit lorsque l'�v�nement de type T est d�clench�.
     * @param event L'event d�clench�.
     * @return true si l'event s'est correctement ex�cut�, false sinon.
     */
    @Override
    public boolean onEventTriggered(EntityPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();

        if (!(event.getEntity() instanceof Player player && item.getType() == super.target() && !super.hasElemPlayer(player, item))) {
            return false;
        }

        super.incrementCount();
        super.addPlayerToElem(player, item);

        return super.getCount() >= super.getAmount();
    }
}
