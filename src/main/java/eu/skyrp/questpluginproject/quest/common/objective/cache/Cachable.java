package eu.skyrp.questpluginproject.quest.common.objective.cache;

import org.bukkit.entity.Player;

/**
 * Représente un élément (souvent {@link org.bukkit.block.Block} ou {@link org.bukkit.inventory.ItemStack}) avec lequel un joueur a déjà eu des interactions.
 * @param <T> Le type de l'élément qui doit avoir un cache.
 */
public interface Cachable<T> {

    /**
     * Vérifie si l'élément a déjà le joueur en cache.
     * @param player Le joueur en question.
     * @param elem L'élément en question.
     * @return true si l'élément a le joueur en cache, false sinon.
     */
    boolean hasElemPlayer(Player player, T elem);

    /**
     * Ajoute le joueur dans le cache de l'élément.
     * @param player Le joueur en question.
     * @param elem L'élément en question.
     */
    void addPlayerToElem(Player player, T elem);

}
