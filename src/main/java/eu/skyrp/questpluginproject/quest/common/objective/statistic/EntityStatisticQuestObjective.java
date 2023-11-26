package eu.skyrp.questpluginproject.quest.common.objective.statistic;

import eu.skyrp.questpluginproject.quest.common.objective.Countable;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

import java.util.UUID;

/**
 * Classe qui repr�sente un objectif de qu�te dont l'avanc�e peut �tre g�r�e par une Statistic et dont la cible est
 * de type EntityType.
 * @see Statistic
 */
public class EntityStatisticQuestObjective extends BaseStatisticQuestObjective<EntityType> {

    /**
     * Constructeur de la classe EntityStatisticQuestObjective.
     * @param id Id de la qu�te.
     * @param playerUUID UUID du joueur concern� par la qu�te.
     * @param target Objet inclus dans l'objectif de qu�te.
     * @param type Type de Statistic � v�rifier.
     * @param amount Nombre requis pour atteindre le bout de la qu�te.
     */
    public EntityStatisticQuestObjective(String id, UUID playerUUID, Statistic type, EntityType target, int amount) {
        super(id, playerUUID, type, target, amount);
    }

    /**
     * Obtenir l'avanc�e du joueur sur sa qu�te sous forme de nombre entier.
     * @return Avanc�e du joueur sous forme de nombre entier.
     * @see Countable#getCount()
     */
    @Override
    public final int getCount() {
        return super.player().getStatistic(this.type, super.target);
    }

}
