package eu.skyrp.questpluginproject.quest.custom.itemsadder.objective;

import dev.lone.itemsadder.api.CustomStack;
import eu.skyrp.questpluginproject.quest.custom.common.CustomObjectFinder;
import eu.skyrp.questpluginproject.quest.common.objective.cache.BaseItemQuestObjective;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class IACollectQuestObjective extends BaseItemQuestObjective<EntityPickupItemEvent, CustomStack> {
    /**
     * Constructeur de la classe ItemStatisticQuestObjective.
     *
     * @param id         Id de la qu�te.
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de qu�te.
     * @param amount     Nombre requis pour atteindre le bout de la qu�te.
     * @param plugin     Instance de la classe principale du plugin.
     */
    public IACollectQuestObjective(String id, String targetId, int amount, JavaPlugin plugin) {
        super(
                EntityPickupItemEvent.class,
                id,
                CustomObjectFinder.getById(
                        targetId,
                        "ItemsAdder",
                        "ia",
                        CustomStack::isInRegistry,
                        CustomStack::getInstance
                ),
                amount,
                plugin
        );
    }

    /**
     * R�agit lorsque l'�v�nement de type T est d�clench�.
     * @param event L'event d�clench�.
     * @return true si l'event s'est correctement ex�cut�, false sinon.
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

        return super.getCount() >= super.getAmount();
    }
}
