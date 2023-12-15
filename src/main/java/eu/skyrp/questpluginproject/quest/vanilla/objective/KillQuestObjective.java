package eu.skyrp.questpluginproject.quest.vanilla.objective;

import eu.skyrp.questpluginproject.quest.common.objective.statistic.BaseStatisticQuestObjective;
import lombok.Builder;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

public class KillQuestObjective extends BaseStatisticQuestObjective<EntityType> {

    /**
     * Constructeur de la classe BaseStatisticQuestObjective.
     * @param id Id de la quête.
     * @param target Objet inclus dans l'objectif de quête.
     * @param amount Nombre requis pour atteindre le bout de la quête.
     */
    @Builder
    public KillQuestObjective(String id, EntityType target, int amount) {
        super(id, Statistic.PLAYER_KILLS, target, amount);
    }

}
