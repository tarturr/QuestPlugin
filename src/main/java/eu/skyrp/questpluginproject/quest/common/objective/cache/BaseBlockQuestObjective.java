package eu.skyrp.questpluginproject.quest.common.objective.cache;

import eu.skyrp.questpluginproject.quest.common.objective.BaseCountableQuestObjective;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public abstract class BaseBlockQuestObjective<T extends Event, U> extends BaseCountableQuestObjective<T, U> implements Cachable<Block> {

    private final JavaPlugin plugin;

    /**
     * @param id         Id de la qu�te.
     * @param playerUUID UUID du joueur concern� par la qu�te.
     * @param target     Objet inclus dans l'objectif de qu�te.
     * @param amount     Nombre requis pour atteindre le bout de la qu�te.
     * @param plugin     Instance de la classe principale du plugin.
     */
    public BaseBlockQuestObjective(String id, UUID playerUUID, U target, int amount, JavaPlugin plugin) {
        super(id, playerUUID, target, amount);
        this.plugin = plugin;
    }

    protected abstract Player getEventPlayer(T event);

    /**
     * V�rifie si le block a d�j� le joueur en cache.
     * @param player Le joueur en question.
     * @param elem Le block en question.
     * @return true si le block a le joueur en cache, false sinon.
     */
    @Override
    public final boolean hasElemPlayer(Player player, Block elem) {
        return elem.hasMetadata(player.getUniqueId().toString());
    }

    /**
     * Ajoute le joueur dans le cache du block.
     * @param player Le joueur en question.
     * @param elem Le block en question.
     */
    @Override
    public final void addPlayerToElem(Player player, Block elem) {
        elem.setMetadata(player.getUniqueId().toString(), new FixedMetadataValue(this.plugin, true));
    }
}
