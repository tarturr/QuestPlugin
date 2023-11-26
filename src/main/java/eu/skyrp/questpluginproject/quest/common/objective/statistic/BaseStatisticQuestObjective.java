package eu.skyrp.questpluginproject.quest.common.objective.statistic;

import eu.skyrp.questpluginproject.quest.common.objective.BaseQuestObjective;
import eu.skyrp.questpluginproject.quest.common.objective.Countable;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

import java.util.UUID;


/**
 * Classe qui repr�sente un objectif de qu�te dont l'avanc�e peut �tre g�r�e par une Statistic.
 * @param <T> Type de la cible incluse dans l'objectif de qu�te.
 * @see Statistic
 */
public abstract class BaseStatisticQuestObjective<T> extends BaseQuestObjective<PlayerStatisticIncrementEvent, T> implements Countable {

    protected final Statistic type;
    protected final int amount;

    /**
     * Constructeur de la classe BaseStatisticQuestObjective.
     * @param id Id de la qu�te.
     * @param playerUUID UUID du joueur concern� par la qu�te.
     * @param target Objet inclus dans l'objectif de qu�te.
     * @param type Type de Statistic � v�rifier.
     * @param amount Nombre requis pour atteindre le bout de la qu�te.
     */
    public BaseStatisticQuestObjective(String id, UUID playerUUID, Statistic type, T target, int amount) {
        super(id, playerUUID, target);
        this.type = type;
        this.amount = amount + this.getCount();
    }

    /**
     * R�agit lorsque l'�v�nement de type PlayerStatisticIncrementEvent est d�clench�.
     * @param event L'event d�clench�.
     * @see BaseQuestObjective#onEventTriggered(Event)
     */
    @EventHandler
    @Override
    public void onEventTriggered(PlayerStatisticIncrementEvent event) {
        if (!(event.getStatistic() == this.type && (event.getMaterial() == super.target || event.getEntityType() == super.target))) {
            return;
        }

        Player player = super.player();

        if (!(event.getPlayer() == player && this.getCount() >= this.getAmount())) {
            return;
        }

        player.sendMessage("�a[Quests] Qu�te termin�e !");
    }

    /**
     * Obtienir un montant de l'objectif requis.
     * @return Le montant de l'objectif requis.
     * @see Countable#getAmount()
     */
    @Override
    public final int getAmount() {
        return this.amount;
    }

}
