package eu.skyrp.questpluginproject.quest.common.objective;

/**
 * Interface permettant de compter l'avancée d'une quête.
 */
public interface Countable {

    /**
     * Obtenir l'avancée du joueur sur sa quête sous forme de nombre entier.
     * @return Avancée du joueur sous forme de nombre entier.
     */
    int getCount();

    /**
     * Obtienir un montant de l'objectif requis.
     * @return Le montant de l'objectif requis.
     */
    int getAmount();

}
