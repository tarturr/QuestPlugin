package eu.skyrp.questpluginproject.quest.common.objective.statistic;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class PlayerStatisticQuestObjective extends BaseStatisticQuestObjective<Player> {

    /**
     * Constructeur de la classe PlayerStatisticQuestObjective.
     *
     * @param id         Id de la quête.
     * @param type       Type de Statistic à vérifier.
     * @param target     Objet inclus dans l'objectif de quête.
     * @param amount     Nombre de secondes requises pour atteindre le bout de la quête.
     */
    public PlayerStatisticQuestObjective(String id, Statistic type, Player target, int amount) {
        super(id, type, target, amount * 20);
    }
}
