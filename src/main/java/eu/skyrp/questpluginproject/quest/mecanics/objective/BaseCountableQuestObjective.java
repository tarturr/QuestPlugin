package eu.skyrp.questpluginproject.quest.mecanics.objective;

import org.bukkit.event.Event;

import java.util.UUID;

/**
 * Classe qui repr�sente un objectif de qu�te dont l'avanc�e peut �tre not�e sous forme de chiffre.
 * @param <T> Type d'Event bukkit qui doit �tre appel�.
 * @param <U> Type de la cible incluse dans l'objectif de qu�te.
 */
public abstract class BaseCountableQuestObjective<T extends Event, U> extends BaseQuestObjective<T, U> implements Countable {

    protected final int amount;
    protected int count;

    /**
     * @param id Id de la qu�te.
     * @param playerUUID UUID du joueur concern� par la qu�te.
     * @param target Objet inclus dans l'objectif de qu�te.
     * @param amount Nombre requis pour atteindre le bout de la qu�te.
     */
    public BaseCountableQuestObjective(String id, UUID playerUUID, U target, int amount) {
        super(id, playerUUID, target);
        this.amount = amount;
    }

    /**
     * Augmenter l'avanc�e de la qu�te.
     */
    protected void incrementCount() {
        ++this.count;
    }

    /**
     * Obtenir l'avanc�e du joueur sur sa qu�te sous forme de nombre entier.
     * @return Avanc�e du joueur sous forme de nombre entier.
     */
    @Override
    public final int getCount() {
        return this.count;
    }

    /**
     * Obtienir un montant de l'objectif requis
     * @return Le montant de l'objectif requis.
     */
    @Override
    public final int getAmount() {
        return this.amount;
    }
}
