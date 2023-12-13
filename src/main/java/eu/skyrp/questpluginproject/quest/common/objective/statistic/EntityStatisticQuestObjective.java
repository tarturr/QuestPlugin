package eu.skyrp.questpluginproject.quest.common.objective.statistic;

import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

/**
 * Classe qui repr�sente un objectif de qu�te dont l'avanc�e peut �tre g�r�e par une Statistic et dont la cible est
 * de type EntityType.
 * @see Statistic
 */
public class EntityStatisticQuestObjective extends BaseStatisticQuestObjective<EntityType> {

    /**
     * Constructeur de la classe EntityStatisticQuestObjective.
     * @param id Id de la qu�te.
     * @param target Objet inclus dans l'objectif de qu�te.
     * @param type Type de Statistic � v�rifier.
     * @param amount Nombre requis pour atteindre le bout de la qu�te.
     */
    public EntityStatisticQuestObjective(String id, Statistic type, EntityType target, int amount) {
        super(id, type, target, amount);
    }
}
