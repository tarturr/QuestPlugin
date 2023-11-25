package eu.skyrp.questpluginproject.quest.mecanics.objective.all.other.slimefun;

import eu.skyrp.questpluginproject.quest.mecanics.objective.all.other.CustomObjectFinder;
import eu.skyrp.questpluginproject.quest.mecanics.objective.cache.BaseItemQuestObjective;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class SFCollectQuestObjective extends BaseItemQuestObjective<EntityPickupItemEvent, SlimefunItem> {
    /**
     * Constructeur de la classe ItemStatisticQuestObjective.
     *
     * @param id         Id de la quête.
     * @param playerUUID UUID du joueur concerné par la quête.
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de quête.
     * @param amount     Nombre requis pour atteindre le bout de la quête.
     * @param plugin     Instance de la classe principale du plugin.
     */
    public SFCollectQuestObjective(String id, UUID playerUUID, String targetId, int amount, JavaPlugin plugin) {
        super(
                id,
                playerUUID,
                CustomObjectFinder.getById(
                        targetId,
                        "SlimeFun",
                        "sf",
                        (item) -> SlimefunItem.getById(item) == null,
                        SlimefunItem::getById
                ),
                amount,
                plugin
        );
    }

    /**
     * Réagit lorsque l'événement de type T est déclenché.
     *
     * @param event L'event déclenché.
     */
    @Override
    @EventHandler
    public void onEventTriggered(EntityPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
        SlimefunItem slimefunItem = SlimefunItem.getByItem(item);

        if (!(event.getEntity() instanceof Player player && player == super.player() && slimefunItem != null && slimefunItem.equals(super.target()) && !super.hasElemPlayer(player, item))) {
            return;
        }

        super.incrementCount();
        super.addPlayerToElem(player, item);

        if (super.getCount() >= super.getAmount()) {
            player.sendMessage("§a[Quests] Quête terminée !");
        }
    }
}
