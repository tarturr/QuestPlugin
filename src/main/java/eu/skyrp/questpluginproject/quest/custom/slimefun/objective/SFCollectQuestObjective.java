package eu.skyrp.questpluginproject.quest.custom.slimefun.objective;

import eu.skyrp.questpluginproject.quest.custom.common.CustomObjectFinder;
import eu.skyrp.questpluginproject.quest.common.objective.cache.BaseItemQuestObjective;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class SFCollectQuestObjective extends BaseItemQuestObjective<EntityPickupItemEvent, SlimefunItem> {
    /**
     * Constructeur de la classe ItemStatisticQuestObjective.
     *
     * @param id         Id de la quête.
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de quête.
     * @param amount     Nombre requis pour atteindre le bout de la quête.
     */
    public SFCollectQuestObjective(String id, String targetId, int amount) {
        super(
                EntityPickupItemEvent.class,
                id,
                CustomObjectFinder.getById(
                        targetId,
                        "SlimeFun",
                        "sf",
                        (item) -> SlimefunItem.getById(item) == null,
                        SlimefunItem::getById
                ),
                amount
        );
    }

    /**
     * Réagit lorsque l'événement de type T est déclenché.
     * @param event L'event déclenché.
     * @return true si l'event s'est correctement exécuté, false sinon.
     */
    @Override
    public boolean onEventTriggered(EntityPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
        SlimefunItem slimefunItem = SlimefunItem.getByItem(item);

        if (!(event.getEntity() instanceof Player player && slimefunItem != null && slimefunItem.equals(super.target()) && !super.hasElemPlayer(player, item))) {
            return false;
        }

        super.incrementCount();
        super.addPlayerToElem(player, item);

        return super.count() >= super.amount();
    }
}
