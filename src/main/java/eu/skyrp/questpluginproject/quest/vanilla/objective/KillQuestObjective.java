package eu.skyrp.questpluginproject.quest.vanilla.objective;

import eu.skyrp.questpluginproject.quest.common.objective.statistic.BaseStatisticQuestObjective;
import eu.skyrp.questpluginproject.quest.common.types.ObjectiveType;
import lombok.Builder;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

public class KillQuestObjective extends BaseStatisticQuestObjective<EntityType> {

    /**
     * Constructeur de la classe BaseStatisticQuestObjective.
     * @param id Id de la qu�te.
     * @param target Objet inclus dans l'objectif de qu�te.
     * @param amount Nombre requis pour atteindre le bout de la qu�te.
     */
    @Builder
    public KillQuestObjective(String id, EntityType target, int amount) {
        super(id, ObjectiveType.KILL, Statistic.PLAYER_KILLS, target, amount);
    }

}
