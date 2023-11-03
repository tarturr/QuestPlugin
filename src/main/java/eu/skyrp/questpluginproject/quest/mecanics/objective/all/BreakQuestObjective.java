package eu.skyrp.questpluginproject.quest.mecanics.objective.all;

import eu.skyrp.questpluginproject.quest.mecanics.objective.statistic.MaterialStatisticQuestObjective;
import lombok.Builder;
import org.bukkit.Material;
import org.bukkit.Statistic;

import java.util.UUID;

public class BreakQuestObjective extends MaterialStatisticQuestObjective {

    @Builder
    public BreakQuestObjective(String id, UUID player, Material targetType, int amount) {
        super(id, player, Statistic.MINE_BLOCK, targetType, amount);
    }

}
