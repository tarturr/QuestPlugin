package eu.skyrp.questpluginproject.quest.common.objective;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.beans.PropertyChangeSupport;
import java.util.UUID;

/**
 * Classe qui représente un objectif de quête basique.
 * @param <T> Type d'Event bukkit qui doit être appelé.
 * @param <U> Type de la cible incluse dans l'objectif de quête.
 */
@Getter
@Accessors(fluent = true)
public abstract class BaseQuestObjective<T extends Event, U> implements Listener {

    protected final String id;
    protected final UUID playerUUID;
    protected final U target;
    protected int amount;
    private int count;
    protected boolean hasEnded;

    protected final PropertyChangeSupport endQuestSupport;

    /**
     * Constructeur de la classe BaseQuestObjective.
     * @param id Id de la quête.
     * @param playerUUID UUID du joueur concerné par la quête.
     * @param target Objet inclus dans l'objectif de quête.
     */
    public BaseQuestObjective(String id, UUID playerUUID, U target, int amount) {
        this.id = id;
        this.playerUUID = playerUUID;
        this.target = target;
        this.amount = amount;
        this.count = 0;
        this.hasEnded = false;

        this.endQuestSupport = new PropertyChangeSupport(this);
    }

    @EventHandler
    public void onEvent(T event) {
        if (this.onEventTriggered(event) && !this.hasEnded) {
            this.hasEnded = true;
            this.endQuestSupport.firePropertyChange("hasEnded", false, true);
        }
    }

    /**
     * Réagit lorsque l'événement de type T est déclenché.
     * @param event L'event déclenché.
     */
    public abstract boolean onEventTriggered(T event);

    /**
     * Obtenir le joueur
     * @return Instance de l'objet
     */
    public Player player() {
        return Bukkit.getPlayer(this.playerUUID);
    }

    /**
     * Obtenir l'avancée du joueur sur sa quête sous forme de nombre entier.
     * @return Avancée du joueur sous forme de nombre entier.
     */
    public final int getAmount() {
        return this.amount;
    }

    protected final void incrementCount() {
        ++this.count;
    }

    /**
     * Obtenir l'avancée du joueur sur sa quête sous forme de nombre entier.
     * @return Avancée du joueur sous forme de nombre entier.
     */
    public int getCount() {
        return this.count;
    }

}
