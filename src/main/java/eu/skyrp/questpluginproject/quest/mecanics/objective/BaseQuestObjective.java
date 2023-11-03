package eu.skyrp.questpluginproject.quest.mecanics.objective;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

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

    /**
     * Constructeur de la classe BaseQuestObjective.
     * @param id Id de la quête.
     * @param playerUUID UUID du joueur concerné par la quête.
     * @param target Objet inclus dans l'objectif de quête.
     */
    public BaseQuestObjective(String id, UUID playerUUID, U target) {
        this.id = id;
        this.playerUUID = playerUUID;
        this.target = target;
    }

    /**
     * Réagit lorsque l'événement est appelé
     * @param event event appelé
     */
    public abstract void onEventTriggered(T event);

    /**
     * Obtenir le joueur
     * @return Instance de l'objet
     */
    public Player player() {
        return Bukkit.getPlayer(this.playerUUID);
    }

}
