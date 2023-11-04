package eu.skyrp.questpluginproject.quest.mecanics.objective;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import java.util.UUID;

/**
 * Classe qui repr�sente un objectif de qu�te basique.
 * @param <T> Type d'Event bukkit qui doit �tre appel�.
 * @param <U> Type de la cible incluse dans l'objectif de qu�te.
 */
@Getter
@Accessors(fluent = true)
public abstract class BaseQuestObjective<T extends Event, U> implements Listener {

    protected final String id;
    protected final UUID playerUUID;
    protected final U target;

    /**
     * Constructeur de la classe BaseQuestObjective.
     * @param id Id de la qu�te.
     * @param playerUUID UUID du joueur concern� par la qu�te.
     * @param target Objet inclus dans l'objectif de qu�te.
     */
    public BaseQuestObjective(String id, UUID playerUUID, U target) {
        this.id = id;
        this.playerUUID = playerUUID;
        this.target = target;
    }

    /**
     * R�agit lorsque l'�v�nement de type T est d�clench�.
     * @param event L'event d�clench�.
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
