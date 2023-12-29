package eu.skyrp.questpluginproject.quest.common.objective.cache;

import eu.skyrp.questpluginproject.quest.common.objective.BaseQuestObjective;
import eu.skyrp.questpluginproject.quest.common.types.MechanicType;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class BaseBlockQuestObjective<T extends Event, U> extends BaseQuestObjective<T, U> implements Cachable<Block> {

    private FixedMetadataValue metadata;

    /**
     * @param id         Id de la quête.
     * @param target     Objet inclus dans l'objectif de quête.
     * @param amount     Nombre requis pour atteindre le bout de la quête.
     */
    public BaseBlockQuestObjective(Class<T> eventType, MechanicType type, String id, U target, int amount) {
        super(eventType, type, id, target, amount);
    }

    protected abstract Player getEventPlayer(T event);

    /**
     * Vérifie si le block a déjà le joueur en cache.
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
        if (this.metadata == null) {
            throw new IllegalStateException("[QuestPlugin] The metadata of a block quest objective is missing. " +
                    "Please consider the use of BaseBlockQuestObjective#initMetaData(JavaPlugin).");
        }

        elem.setMetadata(player.getUniqueId().toString(), this.metadata);
    }

    public void initMetaData(JavaPlugin plugin) {
        this.metadata = new FixedMetadataValue(plugin, true);
    }
}
