package eu.skyrp.questpluginproject.quest.custom.itemsadder.objective;

import dev.lone.itemsadder.api.CustomStack;
import eu.skyrp.questpluginproject.quest.custom.common.CustomObjectFinder;
import eu.skyrp.questpluginproject.quest.common.objective.cache.BaseItemQuestObjective;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class IACollectQuestObjective extends BaseItemQuestObjective<EntityPickupItemEvent, CustomStack> {
    /**
     * Constructeur de la classe ItemStatisticQuestObjective.
     *
     * @param id         Id de la quête.
     * @param playerUUID UUID du joueur concerné par la quête.
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de quête.
     * @param amount     Nombre requis pour atteindre le bout de la quête.
     * @param plugin     Instance de la classe principale du plugin.
     */
    public IACollectQuestObjective(String id, UUID playerUUID, String targetId, int amount, JavaPlugin plugin) {
        super(
                id,
                playerUUID,
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

    @Override
    @EventHandler
    public void onEventTriggered(EntityPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
        CustomStack custom = CustomStack.byItemStack(item);

        if (!(event.getEntity() instanceof Player player && player == super.player() && custom != null && custom.equals(super.target()) && !super.hasElemPlayer(player, item))) {
            return;
        }

        super.incrementCount();
        super.addPlayerToElem(player, item);

        if (super.getCount() >= super.getAmount()) {
            player.sendMessage("§a[Quests] Quête terminée !");
        }
    }
}
