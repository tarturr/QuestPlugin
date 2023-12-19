package eu.skyrp.questpluginproject.quest.common.objective;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.jetbrains.annotations.NotNull;

import java.beans.PropertyChangeSupport;

/**
 * Classe qui représente un objectif de quête basique.
 * @param <T> Type d'Event bukkit qui doit être appelé.
 * @param <U> Type de la cible incluse dans l'objectif de quête.
 */
@Getter
@Accessors(fluent = true)
public abstract class BaseQuestObjective<T extends Event, U> implements Listener, EventExecutor {

    private final Class<T> eventType;

    protected final String id;
    protected final U target;
    protected int amount;
    private int count;
    protected boolean hasEnded;

    protected final PropertyChangeSupport endQuestSupport;

    /**
     * Constructeur de la classe BaseQuestObjective.
     * @param id Id de la quête.
     * @param target Objet inclus dans l'objectif de quête.
     */
    public BaseQuestObjective(Class<T> eventType, String id, U target, int amount) {
        this.eventType = eventType;

        this.id = id;
        this.target = target;
        this.amount = amount;
        this.count = 0;
        this.hasEnded = false;

        this.endQuestSupport = new PropertyChangeSupport(this);
    }

    @EventHandler
    public void onEvent(T event) {
        if (!this.hasEnded && this.onEventTriggered(event)) {
            this.hasEnded = true;
            this.endQuestSupport.firePropertyChange("hasEnded", false, true);
        }
    }

    /**
     * Réagit lorsque l'événement de type T est déclenché.
     * @param event L'event déclenché.
     * @return true si l'event s'est correctement exécuté, false sinon.
     */
    public abstract boolean onEventTriggered(T event);

    protected final void incrementCount() {
        ++this.count;
    }

    @Override
    public void execute(@NotNull Listener listener, @NotNull Event event) {
        this.onEvent((T) event);
    }

}
