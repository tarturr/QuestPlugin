package eu.skyrp.questpluginproject.quest.common.objective.statistic;

import eu.skyrp.questpluginproject.quest.common.objective.BaseQuestObjective;
import org.bukkit.Statistic;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

import java.util.UUID;


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
     * @param playerUUID UUID du joueur concern� par la qu�te.
     * @param target Objet inclus dans l'objectif de qu�te.
     * @param type Type de Statistic � v�rifier.
     * @param amount Nombre requis pour atteindre le bout de la qu�te.
     */
    public BaseStatisticQuestObjective(String id, UUID playerUUID, Statistic type, T target, int amount) {
        super(id, playerUUID, target, amount);
        this.type = type;
        super.amount = amount + this.getCount();
    }

    /**
     * R�agit lorsque l'�v�nement de type PlayerStatisticIncrementEvent est d�clench�.
     * @param event L'event d�clench�.
     * @see BaseQuestObjective#onEventTriggered(Event)
     */
    @Override
    public boolean onEventTriggered(PlayerStatisticIncrementEvent event) {
        if (!(event.getStatistic() == this.type && (event.getMaterial() == super.target || event.getEntityType() == super.target))) {
            return false;
        }

        return event.getPlayer() == super.player() && this.getCount() >= this.getAmount();
    }

}
