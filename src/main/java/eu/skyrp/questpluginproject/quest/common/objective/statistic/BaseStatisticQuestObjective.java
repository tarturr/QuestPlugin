package eu.skyrp.questpluginproject.quest.common.objective.statistic;

import eu.skyrp.questpluginproject.quest.common.objective.BaseQuestObjective;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;


/**
 * Classe qui repr�sente un objectif de qu�te dont l'avanc�e peut �tre g�r�e par une Statistic.
 * @param <T> Type de la cible incluse dans l'objectif de qu�te.
 * @see Statistic
 */
public abstract class BaseStatisticQuestObjective<T> extends BaseQuestObjective<PlayerStatisticIncrementEvent, T> {

    protected final Statistic type;

    /**
     * Constructeur de la classe BaseStatisticQuestObjective.
     * @param id Id de la qu�te.
     * @param target Objet inclus dans l'objectif de qu�te.
     * @param type Type de Statistic � v�rifier.
     * @param amount Nombre requis pour atteindre le bout de la qu�te.
     */
    public BaseStatisticQuestObjective(String id, Statistic type, T target, int amount) {
        super(PlayerStatisticIncrementEvent.class, id, target, amount);
        this.type = type;
        super.amount = amount + this.getCount();
    }

    /**
     * R�agit lorsque l'�v�nement de type PlayerStatisticIncrementEvent est d�clench�.
     * @param event L'event d�clench�.
     * @return true si l'event s'est correctement ex�cut�, false sinon.
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
