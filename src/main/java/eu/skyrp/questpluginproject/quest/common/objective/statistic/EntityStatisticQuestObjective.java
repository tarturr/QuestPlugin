package eu.skyrp.questpluginproject.quest.common.objective.statistic;

import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

/**
 * Classe qui représente un objectif de quête dont l'avancée peut être gérée par une Statistic et dont la cible est
 * de type EntityType.
 * @see Statistic
 */
public class EntityStatisticQuestObjective extends BaseStatisticQuestObjective<EntityType> {

    /**
     * Constructeur de la classe EntityStatisticQuestObjective.
     * @param id Id de la quête.
     * @param target Objet inclus dans l'objectif de quête.
     * @param type Type de Statistic à vérifier.
     * @param amount Nombre requis pour atteindre le bout de la quête.
     */
    public EntityStatisticQuestObjective(String id, Statistic type, EntityType target, int amount) {
        super(id, type, target, amount);
    }
}
