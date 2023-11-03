package eu.skyrp.questpluginproject.quest.mecanics.objective.all;

import eu.skyrp.questpluginproject.quest.mecanics.objective.statistic.MaterialStatisticQuestObjective;
import lombok.Builder;
import org.bukkit.Material;
import org.bukkit.Statistic;

import java.util.UUID;

public class CollectQuestObjective extends MaterialStatisticQuestObjective {

    @Builder
    public CollectQuestObjective(String id, UUID player, Material targetType, int toReach) {
        super(id, player, Statistic.PICKUP, targetType, toReach);
    }

}
