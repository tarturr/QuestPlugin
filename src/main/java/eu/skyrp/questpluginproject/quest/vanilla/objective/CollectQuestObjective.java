package eu.skyrp.questpluginproject.quest.vanilla.objective;

import eu.skyrp.questpluginproject.quest.common.objective.cache.BaseItemQuestObjective;
import lombok.Builder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class CollectQuestObjective extends BaseItemQuestObjective<EntityPickupItemEvent, Material> {

    /**
     * Constructeur de la classe ItemStatisticQuestObjective.
     *
     * @param id         Id de la quête.
     * @param playerUUID UUID du joueur concerné par la quête.
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de quête.
     * @param amount     Nombre requis pour atteindre le bout de la quête.
     * @param plugin     Instance de la classe principale du plugin.
     */
    @Builder
    public CollectQuestObjective(String id, UUID playerUUID, String targetId, int amount, JavaPlugin plugin) {
        super(id, playerUUID, Material.valueOf(targetId.toUpperCase()), amount, plugin);

        if (super.target() == null) {
            throw new IllegalArgumentException("The \"" + targetId + "\" item type could not be recognized.");
        }
    }

    @Override
    @EventHandler
    public void onEventTriggered(EntityPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();

        if (!(event.getEntity() instanceof Player player && player == super.player() && item.getType() == super.target() && !super.hasElemPlayer(player, item))) {
            return;
        }

        super.incrementCount();
        super.addPlayerToElem(player, item);

        if (super.getCount() >= super.getAmount()) {
            player.sendMessage("§a[Quests] Quête terminée !");
        }
    }
}
