package eu.skyrp.questpluginproject.quest.vanilla.objective;

import eu.skyrp.questpluginproject.quest.common.objective.statistic.PlayerStatisticQuestObjective;
import lombok.Builder;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class ConnectQuestObjective extends PlayerStatisticQuestObjective {

    @Builder
    public ConnectQuestObjective(String id, Player target, int amount) {
        super(id, Statistic.PLAY_ONE_MINUTE, target, amount);
    }

}
