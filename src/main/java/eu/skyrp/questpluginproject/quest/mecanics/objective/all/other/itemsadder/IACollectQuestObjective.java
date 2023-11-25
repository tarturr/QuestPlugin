package eu.skyrp.questpluginproject.quest.mecanics.objective.all.other.itemsadder;

import dev.lone.itemsadder.api.CustomStack;
import eu.skyrp.questpluginproject.quest.mecanics.objective.all.other.CustomItemStackFinder;
import eu.skyrp.questpluginproject.quest.mecanics.objective.cache.BaseItemQuestObjective;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class IACollectQuestObjective extends BaseItemQuestObjective<EntityPickupItemEvent, ItemStack> {
    /**
     * Constructeur de la classe ItemStatisticQuestObjective.
     *
     * @param id         Id de la qu�te.
     * @param playerUUID UUID du joueur concern� par la qu�te.
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de qu�te.
     * @param amount     Nombre requis pour atteindre le bout de la qu�te.
     * @param plugin     Instance de la classe principale du plugin.
     */
    public IACollectQuestObjective(String id, UUID playerUUID, String targetId, int amount, JavaPlugin plugin) {
        super(
                id,
                playerUUID,
                CustomItemStackFinder.getItemById(
                        targetId,
                        "ItemsAdder",
                        "ia",
                        CustomStack::isInRegistry,
                        (item) -> CustomStack.getInstance(item).getItemStack()
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

        if (!(event.getEntity() instanceof Player player && player == super.player() && custom != null && item.isSimilar(super.target()) && !super.hasElemPlayer(player, item))) {
            return;
        }

        super.incrementCount();
        super.addPlayerToElem(player, item);

        if (super.getCount() >= super.getAmount()) {
            player.sendMessage("�a[Quests] Qu�te termin�e !");
        }
    }
}
