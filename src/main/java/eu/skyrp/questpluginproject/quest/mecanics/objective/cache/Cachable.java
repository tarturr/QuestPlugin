package eu.skyrp.questpluginproject.quest.mecanics.objective.cache;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Repr�sente un �l�ment (souvent {@link org.bukkit.block.Block} ou {@link org.bukkit.inventory.ItemStack}) avec lequel un joueur a d�j� eu des interactions.
 * @param <T> Le type de l'�l�ment qui doit avoir un cache.
 */
public interface Cachable<T> {

    /**
     * V�rifie si l'�l�ment a d�j� le joueur en cache.
     * @param player Le joueur en question.
     * @param elem L'�l�ment en question.
     * @return true si l'�l�ment a le joueur en cache, false sinon.
     */
    boolean hasElemPlayer(Player player, T elem);

    /**
     * Ajoute le joueur dans le cache de l'�l�ment.
     * @param player Le joueur en question.
     * @param elem L'�l�ment en question.
     */
    void addPlayerToElem(Player player, T elem);

}
