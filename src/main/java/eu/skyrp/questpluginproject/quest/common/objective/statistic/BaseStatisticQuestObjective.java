package eu.skyrp.questpluginproject.quest.common.objective.statistic;

import eu.skyrp.questpluginproject.quest.common.objective.BaseQuestObjective;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;


/**
 * Classe qui représente un objectif de quête dont l'avancée peut être gérée par une Statistic.
 * @param <T> Type de la cible incluse dans l'objectif de quête.
 * @see Statistic
 */
public abstract class BaseStatisticQuestObjective<T> extends BaseQuestObjective<PlayerStatisticIncrementEvent, T> {

    protected final Statistic type;

    /**
     * Constructeur de la classe BaseStatisticQuestObjective.
     * @param id Id de la quête.
     * @param target Objet inclus dans l'objectif de quête.
     * @param type Type de Statistic à vérifier.
     * @param amount Nombre requis pour atteindre le bout de la quête.
     */
    public BaseStatisticQuestObjective(String id, Statistic type, T target, int amount) {
        super(PlayerStatisticIncrementEvent.class, id, target, amount);
        this.type = type;
        super.amount = amount + this.getCount();
    }

    /**
     * Réagit lorsque l'événement de type PlayerStatisticIncrementEvent est déclenché.
     * @param event L'event déclenché.
     * @return true si l'event s'est correctement exécuté, false sinon.
     * @see BaseQuestObjective#onEventTriggered(Event)
     */
    @Override
    public boolean onEventTriggered(PlayerStatisticIncrementEvent event) {
        if (!(event.getStatistic() == this.type && (super.target == null || super.target == event.getEntityType()))) {
            return false;
        }

        return this.getCount(event.getPlayer()) >= this.getAmount();
    }

    public int getCount(Player player) {
        return player.getStatistic(this.type) / 20;
    }

}
