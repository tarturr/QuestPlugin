package eu.skyrp.questpluginproject.quest.mecanics.objective.all;

import eu.skyrp.questpluginproject.quest.mecanics.objective.statistic.ItemStatisticQuestObjective;
import lombok.Builder;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class BreakQuestObjective extends ItemStatisticQuestObjective {

    @Builder
    public BreakQuestObjective(String id, UUID player, ItemStack target, int amount) {
        super(id, player, Statistic.MINE_BLOCK, target, amount);
    }

}
