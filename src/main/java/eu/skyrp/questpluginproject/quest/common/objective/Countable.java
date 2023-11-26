package eu.skyrp.questpluginproject.quest.common.objective;

/**
 * Interface permettant de compter l'avanc�e d'une qu�te.
 */
public interface Countable {

    /**
     * Obtenir l'avanc�e du joueur sur sa qu�te sous forme de nombre entier.
     * @return Avanc�e du joueur sous forme de nombre entier.
     */
    int getCount();

    /**
     * Obtienir un montant de l'objectif requis.
     * @return Le montant de l'objectif requis.
     */
    int getAmount();

}
