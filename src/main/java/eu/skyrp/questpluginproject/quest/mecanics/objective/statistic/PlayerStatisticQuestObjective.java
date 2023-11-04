package eu.skyrp.questpluginproject.quest.mecanics.objective.statistic;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerStatisticQuestObjective extends BaseStatisticQuestObjective<Player> {

    /**
     * Constructeur de la classe PlayerStatisticQuestObjective.
     *
     * @param id         Id de la qu�te.
     * @param playerUUID UUID du joueur concern� par la qu�te.
     * @param type       Type de Statistic � v�rifier.
     * @param target     Objet inclus dans l'objectif de qu�te.
     * @param amount     Nombre de secondes requises pour atteindre le bout de la qu�te.
     */
    public PlayerStatisticQuestObjective(String id, UUID playerUUID, Statistic type, Player target, int amount) {
        super(id, playerUUID, type, target, amount * 20);
    }

    @Override
    public int getCount() {
        return super.player().getStatistic(this.type) / 20;
    }
}
