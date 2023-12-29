package eu.skyrp.questpluginproject.quest.common.objective;

import eu.skyrp.questpluginproject.lib.database.connection.BaseDatabaseConnection;
import eu.skyrp.questpluginproject.quest.common.dispatcher.QuestObjectiveDispatcher;
import eu.skyrp.questpluginproject.quest.common.init.DatabaseInitializable;
import eu.skyrp.questpluginproject.quest.common.types.MechanicType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.jetbrains.annotations.NotNull;

import java.beans.PropertyChangeSupport;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe qui représente un objectif de quête basique.
 * @param <T> Type d'Event bukkit qui doit être appelé.
 * @param <U> Type de la cible incluse dans l'objectif de quête.
 */
@Getter
@Setter
@Accessors(fluent = true)
public abstract class BaseQuestObjective<T extends Event, U> implements Listener, EventExecutor {

    private final Class<T> eventType;
    private final MechanicType type;

    protected String id;
    protected U target;
    protected int amount;
    private int count;
    protected boolean hasEnded;

    protected final PropertyChangeSupport endQuestSupport;

    /**
     * Constructeur de la classe BaseQuestObjective.
     * @param eventType Le type d'event qui sera utilisé par la quête sous forme d'object {@link Class}.
     * @param id Id de la quête.
     * @param target Objet inclus dans l'objectif de quête.
     * @param amount L'objectif à atteindre sous forme de chiffre
     */
    public BaseQuestObjective(Class<T> eventType, MechanicType type, String id, U target, int amount) {
        this.eventType = eventType;
        this.type = type;

        this.id = id;
        this.target = target;
        this.amount = amount;
        this.count = 0;
        this.hasEnded = false;

        this.endQuestSupport = new PropertyChangeSupport(this);
    }

    public static class Initializer implements DatabaseInitializable<BaseQuestObjective<?, ?>> {

        @Override
        public BaseQuestObjective<?, ?> init(int id, BaseDatabaseConnection connection) {
            try {
                PreparedStatement statement = connection.get().prepareStatement("""
                    SELECT * FROM objective
                    WHERE id = ?
                    """.stripIndent());

                statement.setInt(1, id);

                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    return new QuestObjectiveDispatcher().dispatch(result.getString(2))
                            .amount(result.getInt(3))
                            .count(result.getInt(4));
                }

                return null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

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
