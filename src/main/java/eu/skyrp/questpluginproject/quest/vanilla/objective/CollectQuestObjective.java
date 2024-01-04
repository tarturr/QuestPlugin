package eu.skyrp.questpluginproject.quest.vanilla.objective;

import eu.skyrp.questpluginproject.quest.common.objective.cache.BaseItemQuestObjective;
import eu.skyrp.questpluginproject.quest.common.types.MechanicType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class CollectQuestObjective extends BaseItemQuestObjective<EntityPickupItemEvent, Material> {

    public CollectQuestObjective() {
        this(null, 0);
    }

    /**
     * Constructeur de la classe ItemStatisticQuestObjective.
     *
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de quête.
     * @param amount     Nombre requis pour atteindre le bout de la quête.
     */
    public CollectQuestObjective(String targetId, int amount) {
        super(EntityPickupItemEvent.class, MechanicType.COLLECT, materialFromString(targetId), amount);

        if (super.target() == null) {
            throw new IllegalArgumentException("The \"" + targetId + "\" item type could not be recognized.");
        }
    }

    /**
     * Réagit lorsque l'événement de type T est déclenché.
     * @param event L'event déclenché.
     * @return true si l'event s'est correctement exécuté, false sinon.
     */
    @Override
    public boolean onEventTriggered(EntityPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();

        if (!(event.getEntity() instanceof Player player && item.getType() == super.target() && !super.hasElemPlayer(player, item))) {
            return false;
        }

        super.incrementCount();
        super.addPlayerToElem(player, item);

        return super.count() >= super.amount();
    }

    private static Material materialFromString(String material) {
        return material == null
                ? Material.AIR
                : Material.valueOf(material.toUpperCase());
    }
}
