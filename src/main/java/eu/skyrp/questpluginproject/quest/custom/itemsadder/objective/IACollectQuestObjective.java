package eu.skyrp.questpluginproject.quest.custom.itemsadder.objective;

import dev.lone.itemsadder.api.CustomStack;
import eu.skyrp.questpluginproject.quest.common.types.MechanicType;
import eu.skyrp.questpluginproject.quest.custom.common.CustomObjectFinder;
import eu.skyrp.questpluginproject.quest.common.objective.cache.BaseItemQuestObjective;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class IACollectQuestObjective extends BaseItemQuestObjective<EntityPickupItemEvent, CustomStack> {
    /**
     * Constructeur de la classe ItemStatisticQuestObjective.
     *
     * @param id         Id de la quête.
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de quête.
     * @param amount     Nombre requis pour atteindre le bout de la quête.
     */
    public IACollectQuestObjective(String id, String targetId, int amount) {
        super(EntityPickupItemEvent.class, MechanicType.COLLECT, id, targetId == null ? null : itemFromString(targetId), amount);
    }

    public IACollectQuestObjective() {
        this(null, null, 0);
    }

    /**
     * Réagit lorsque l'événement de type T est déclenché.
     * @param event L'event déclenché.
     * @return true si l'event s'est correctement exécuté, false sinon.
     */
    @Override
    public boolean onEventTriggered(EntityPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
        CustomStack custom = CustomStack.byItemStack(item);

        if (!(event.getEntity() instanceof Player player && custom != null && custom.equals(super.target()) && !super.hasElemPlayer(player, item))) {
            return false;
        }

        super.incrementCount();
        super.addPlayerToElem(player, item);

        return super.count() >= super.amount();
    }

    private static CustomStack itemFromString(String targetId) {
        return CustomObjectFinder.getById(
                targetId,
                "ItemsAdder",
                "ia",
                CustomStack::isInRegistry,
                CustomStack::getInstance
        );
    }
}
