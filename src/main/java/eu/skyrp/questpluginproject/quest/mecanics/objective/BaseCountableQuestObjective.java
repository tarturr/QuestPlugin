package eu.skyrp.questpluginproject.quest.mecanics.objective;

import org.bukkit.event.Event;

import java.util.UUID;

/**
 * Classe qui représente un objectif de quête dont l'avancée peut être notée sous forme de chiffre.
 * @param <T> Type d'Event bukkit qui doit être appelé.
 * @param <U> Type de la cible incluse dans l'objectif de quête.
 */
public abstract class BaseCountableQuestObjective<T extends Event, U> extends BaseQuestObjective<T, U> implements Countable {

    private final int amount;
    private int count;

    /**
     * @param id Id de la quête.
     * @param playerUUID UUID du joueur concerné par la quête.
     * @param target Objet inclus dans l'objectif de quête.
     * @param amount Nombre requis pour atteindre le bout de la quête.
     */
    public BaseCountableQuestObjective(String id, UUID playerUUID, U target, int amount) {
        super(id, playerUUID, target);
        this.amount = amount;
        this.count = 0;
    }

    /**
     * @see Countable#getAmount()
     * @return Le montant de l'objectif requis.
     */
    @Override
    public final int getAmount() {
        return this.amount;
    }

    protected final void incrementCount() {
        ++this.count;
    }

    /**
     * @see Countable#getCount()
     * @return Avancée du joueur sous forme de nombre entier.
     */
    @Override
    public final int getCount() {
        return this.count;
    }
}
