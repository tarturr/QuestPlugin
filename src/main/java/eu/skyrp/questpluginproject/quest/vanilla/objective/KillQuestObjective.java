package eu.skyrp.questpluginproject.quest.vanilla.objective;

import eu.skyrp.questpluginproject.quest.common.objective.statistic.EntityStatisticQuestObjective;
import lombok.Builder;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

public class KillQuestObjective extends EntityStatisticQuestObjective {

    @Builder
    public KillQuestObjective(String id, EntityType targetType, int amount) {
        super(id, Statistic.PLAYER_KILLS, targetType, amount);
    }

}
