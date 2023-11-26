package eu.skyrp.questpluginproject.quest.common.objective.statistic;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerStatisticQuestObjective extends BaseStatisticQuestObjective<Player> {

    /**
     * Constructeur de la classe PlayerStatisticQuestObjective.
     *
     * @param id         Id de la quête.
     * @param playerUUID UUID du joueur concerné par la quête.
     * @param type       Type de Statistic à vérifier.
     * @param target     Objet inclus dans l'objectif de quête.
     * @param amount     Nombre de secondes requises pour atteindre le bout de la quête.
     */
    public PlayerStatisticQuestObjective(String id, UUID playerUUID, Statistic type, Player target, int amount) {
        super(id, playerUUID, type, target, amount * 20);
    }

    @Override
    public int getCount() {
        return super.player().getStatistic(this.type) / 20;
    }
}
