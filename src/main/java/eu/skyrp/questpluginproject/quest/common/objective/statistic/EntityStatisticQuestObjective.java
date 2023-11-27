package eu.skyrp.questpluginproject.quest.common.objective.statistic;

import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

import java.util.UUID;

/**
 * Classe qui représente un objectif de quête dont l'avancée peut être gérée par une Statistic et dont la cible est
 * de type EntityType.
 * @see Statistic
 */
public class EntityStatisticQuestObjective extends BaseStatisticQuestObjective<EntityType> {

    /**
     * Constructeur de la classe EntityStatisticQuestObjective.
     * @param id Id de la quête.
     * @param playerUUID UUID du joueur concerné par la quête.
     * @param target Objet inclus dans l'objectif de quête.
     * @param type Type de Statistic à vérifier.
     * @param amount Nombre requis pour atteindre le bout de la quête.
     */
    public EntityStatisticQuestObjective(String id, UUID playerUUID, Statistic type, EntityType target, int amount) {
        super(id, playerUUID, type, target, amount);
    }

    /**
     * Obtenir l'avancée du joueur sur sa quête sous forme de nombre entier.
     * @return Avancée du joueur sous forme de nombre entier.
     */
    @Override
    public final int getCount() {
        return super.player().getStatistic(this.type, super.target);
    }

}
