package eu.skyrp.questpluginproject.quest.vanilla.objective;

import eu.skyrp.questpluginproject.quest.common.objective.statistic.BaseStatisticQuestObjective;
import lombok.Builder;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class ConnectQuestObjective extends BaseStatisticQuestObjective<Player> {

    /**
     * Constructeur de la classe ConnectQuestObjective.
     *
     * @param id         Id de la quête.
     * @param amount     Nombre de secondes requises pour atteindre le bout de la quête.
     */
    @Builder
    public ConnectQuestObjective(String id, int amount) {
        super(id, Statistic.PLAY_ONE_MINUTE, null, amount);
    }

}
