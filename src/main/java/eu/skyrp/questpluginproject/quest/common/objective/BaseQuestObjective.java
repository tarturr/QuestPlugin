package eu.skyrp.questpluginproject.quest.common.objective;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.jetbrains.annotations.NotNull;

import java.beans.PropertyChangeSupport;
import java.util.UUID;

/**
 * Classe qui repr�sente un objectif de qu�te basique.
 * @param <T> Type d'Event bukkit qui doit �tre appel�.
 * @param <U> Type de la cible incluse dans l'objectif de qu�te.
 */
@Getter
@Accessors(fluent = true)
public abstract class BaseQuestObjective<T extends Event, U> implements Listener, EventExecutor {

    private final Class<T> eventType;

    protected final String id;
    protected final UUID playerUUID;
    protected final U target;
    protected int amount;
    private int count;
    protected boolean hasEnded;

    protected final PropertyChangeSupport endQuestSupport;

    /**
     * Constructeur de la classe BaseQuestObjective.
     * @param id Id de la qu�te.
     * @param playerUUID UUID du joueur concern� par la qu�te.
     * @param target Objet inclus dans l'objectif de qu�te.
     */
    public BaseQuestObjective(Class<T> eventType, String id, UUID playerUUID, U target, int amount) {
        this.eventType = eventType;

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
     * R�agit lorsque l'�v�nement de type T est d�clench�.
     * @param event L'event d�clench�.
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
     * Obtenir l'avanc�e du joueur sur sa qu�te sous forme de nombre entier.
     * @return Avanc�e du joueur sous forme de nombre entier.
     */
    public final int getAmount() {
        return this.amount;
    }

    protected final void incrementCount() {
        ++this.count;
    }

    /**
     * Obtenir l'avanc�e du joueur sur sa qu�te sous forme de nombre entier.
     * @return Avanc�e du joueur sous forme de nombre entier.
     */
    public int getCount() {
        return this.count;
    }

    @Override
    public void execute(@NotNull Listener listener, @NotNull Event event) throws EventException {
        this.onEvent((T) event);
    }

    public Class<T> getEventType() {
        return eventType;
    }
}
