package eu.skyrp.questpluginproject.quest.mecanics.objective.statistic;

import eu.skyrp.questpluginproject.quest.mecanics.objective.BaseQuestObjective;
import eu.skyrp.questpluginproject.quest.mecanics.objective.Countable;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

import java.util.UUID;


/**
 * Classe qui représente un objectif de quête dont l'avancée peut être gérée par une Statistic.
 * @param <T> Type de la cible incluse dans l'objectif de quête.
 * @see Statistic
 */
public abstract class BaseStatisticQuestObjective<T> extends BaseQuestObjective<PlayerStatisticIncrementEvent, T> implements Countable {

    protected final Statistic type;
    protected int amount;

    public BaseStatisticQuestObjective(String id, UUID player, Statistic type, T target, int amount) {
        super(id, player, target);
        this.type = type;
        this.amount = amount;
    }

    @EventHandler
    @Override
    public final void onEventTriggered(PlayerStatisticIncrementEvent event) {
        if (!(event.getStatistic() == this.type && (event.getMaterial() == super.target || event.getEntityType() == super.target))) {
            return;
        }

        Player player = super.player();

        if (!(event.getPlayer() == player && this.getCount() >= this.getAmount())) {
            return;
        }

        player.sendMessage("§a[Quests] Quête terminée !");
    }

    @Override
    public final int getAmount() {
        return this.amount;
    }

}
