package eu.skyrp.questpluginproject.quest.mecanics.objective.statistic;

import eu.skyrp.questpluginproject.quest.mecanics.objective.BaseQuestObjective;
import eu.skyrp.questpluginproject.quest.mecanics.objective.Countable;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
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
    protected final int amount;

    /**
     * Constructeur de la classe BaseStatisticQuestObjective.
     * @param id Id de la quête.
     * @param playerUUID UUID du joueur concerné par la quête.
     * @param target Objet inclus dans l'objectif de quête.
     * @param type Type de Statistic à vérifier.
     * @param amount Nombre requis pour atteindre le bout de la quête.
     */
    public BaseStatisticQuestObjective(String id, UUID playerUUID, Statistic type, T target, int amount) {
        super(id, playerUUID, target);
        this.type = type;
        this.amount = amount + this.getCount();
    }

    /**
     * Réagit lorsque l'événement de type PlayerStatisticIncrementEvent est déclenché.
     * @param event L'event déclenché.
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

        player.sendMessage("§a[Quests] Quête terminée !");
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
