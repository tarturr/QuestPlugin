package eu.skyrp.questpluginproject.quest.mecanics.objective.all;

import eu.skyrp.questpluginproject.quest.mecanics.objective.statistic.EntityStatisticQuestObjective;
import lombok.Builder;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

import java.util.UUID;

public class KillQuestObjective extends EntityStatisticQuestObjective {

    @Builder
    public KillQuestObjective(String id, UUID player, EntityType targetType, int toReach) {
        super(id, player, Statistic.PLAYER_KILLS, targetType, toReach);
    }

}
