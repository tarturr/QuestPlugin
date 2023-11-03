package eu.skyrp.questpluginproject.quest.mecanics.objective.statistic;

import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

import java.util.UUID;

/**
 * Classe qui repr�sente un objectif de qu�te dont l'avanc�e peut �tre g�r�e par une Statistic et dont la cible est
 * de type EntityType.
 * @see Statistic
 */
public class EntityStatisticQuestObjective extends BaseStatisticQuestObjective<EntityType> {

    public EntityStatisticQuestObjective(String id, UUID player, Statistic type, EntityType targetType, int amount) {
        super(id, player, type, targetType, amount);
        super.amount += this.getCount() - 1;
    }

    @Override
    public final int getCount() {
        return super.player().getStatistic(this.type, super.target);
    }

}
