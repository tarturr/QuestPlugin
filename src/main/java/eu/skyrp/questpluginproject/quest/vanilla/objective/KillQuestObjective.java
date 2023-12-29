package eu.skyrp.questpluginproject.quest.vanilla.objective;

import eu.skyrp.questpluginproject.quest.common.objective.statistic.BaseStatisticQuestObjective;
import eu.skyrp.questpluginproject.quest.common.types.MechanicType;
import lombok.Builder;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

public class KillQuestObjective extends BaseStatisticQuestObjective<EntityType> {
    /**
     * Constructeur de la classe BaseStatisticQuestObjective.
     * @param id Id de la qu�te.
     * @param targetId Id (String) du mob inclus dans l'objectif de qu�te.
     * @param amount Nombre requis pour atteindre le bout de la qu�te.
     */
    @Builder
    public KillQuestObjective(String id, String targetId, int amount) {
        super(id, MechanicType.KILL, Statistic.PLAYER_KILLS, entityFromString(targetId), amount);
    }

    public KillQuestObjective() {
        this(null, null, 0);
    }

    private static EntityType entityFromString(String targetId) {
        return targetId == null
                ? null
                : EntityType.valueOf(targetId);
    }
}
