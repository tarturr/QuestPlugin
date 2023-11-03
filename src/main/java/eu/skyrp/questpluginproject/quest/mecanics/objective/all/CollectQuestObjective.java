package eu.skyrp.questpluginproject.quest.mecanics.objective.all;

import eu.skyrp.questpluginproject.quest.mecanics.objective.statistic.ItemStatisticQuestObjective;
import lombok.Builder;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class CollectQuestObjective extends ItemStatisticQuestObjective {

    @Builder
    public CollectQuestObjective(String id, UUID player, ItemStack target, int toReach) {
        super(id, player, Statistic.PICKUP, target, toReach);
    }

}
