package eu.skyrp.questpluginproject.quest.mecanics.objective.all;

import eu.skyrp.questpluginproject.quest.mecanics.objective.BaseCountableQuestObjective;
import lombok.Builder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class CollectQuestObjective extends BaseCountableQuestObjective<EntityPickupItemEvent, ItemStack> {

    @Builder
    public CollectQuestObjective(String id, UUID player, ItemStack target, int toReach) {
        super(id, player, target, toReach);
    }

    @Override
    @EventHandler
    public void onEventTriggered(EntityPickupItemEvent event) {
        // TODO : A faire
    }
}
