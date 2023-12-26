package eu.skyrp.questpluginproject.quest.vanilla.objective;

import eu.skyrp.questpluginproject.quest.common.objective.cache.BaseBlockQuestObjective;
import eu.skyrp.questpluginproject.quest.common.types.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;

public abstract class BaseVanillaBlockQuestObjective<T extends BlockEvent> extends BaseBlockQuestObjective<T, Material> {
    /**
     * @param id         Id de la quête.
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de quête.
     * @param amount     Nombre requis pour atteindre le bout de la quête.
     */
    public BaseVanillaBlockQuestObjective(Class<T> eventType, ObjectiveType type, String id, String targetId, int amount) {
        super(eventType,type, id, Material.valueOf(targetId.toUpperCase()), amount);

        if (super.target() == null) {
            throw new IllegalArgumentException("The \"" + targetId + "\" item type could not be recognized.");
        }
    }

    /**
     * Réagit lorsque l'événement de type T est déclenché.
     * @param event L'event déclenché.
     * @return true si l'event s'est correctement exécuté, false sinon.
     */
    @Override
    public boolean onEventTriggered(T event) {
        Player player = this.getEventPlayer(event);
        Block block = event.getBlock();

        player.sendMessage("Triggered");

        if (!(block.getType() == super.target() && !this.hasElemPlayer(player, block))) {
            return false;
        }

        player.sendMessage("Tests passed");

        this.addPlayerToElem(player, block);
        super.incrementCount();

        return super.count() >= super.amount();
    }
}
