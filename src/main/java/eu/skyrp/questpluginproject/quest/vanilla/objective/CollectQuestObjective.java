package eu.skyrp.questpluginproject.quest.vanilla.objective;

import eu.skyrp.questpluginproject.quest.common.objective.cache.BaseItemQuestObjective;
import eu.skyrp.questpluginproject.quest.common.types.ObjectiveType;
import lombok.Builder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class CollectQuestObjective extends BaseItemQuestObjective<EntityPickupItemEvent, Material> {

    /**
     * Constructeur de la classe ItemStatisticQuestObjective.
     *
     * @param id         Id de la quête.
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de quête.
     * @param amount     Nombre requis pour atteindre le bout de la quête.
     */
    @Builder
    public CollectQuestObjective(String id, String targetId, int amount) {
        super(EntityPickupItemEvent.class, ObjectiveType.COLLECT, id, Material.valueOf(targetId.toUpperCase()), amount);

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
}
