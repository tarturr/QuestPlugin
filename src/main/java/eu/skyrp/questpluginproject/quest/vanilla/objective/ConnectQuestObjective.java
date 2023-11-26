package eu.skyrp.questpluginproject.quest.vanilla.objective;

import eu.skyrp.questpluginproject.quest.common.objective.statistic.PlayerStatisticQuestObjective;
import lombok.Builder;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ConnectQuestObjective extends PlayerStatisticQuestObjective {

    @Builder
    public ConnectQuestObjective(String id, UUID player, Player target, int toReach) {
        super(id, player, Statistic.PLAY_ONE_MINUTE, target, toReach);
    }

}
