package eu.skyrp.questpluginproject.quest.custom.itemsadder.objective;

import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.Events.CustomBlockBreakEvent;
import eu.skyrp.questpluginproject.quest.common.types.MechanicType;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class IABreakQuestObjective extends BaseIABlockQuestObjective<CustomBlockBreakEvent> {
    /**
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de quête.
     * @param amount     Nombre requis pour atteindre le bout de la quête.
     */
    public IABreakQuestObjective(String targetId, int amount) {
        super(CustomBlockBreakEvent.class, MechanicType.BREAK, targetId, amount);
    }

    public IABreakQuestObjective() {
        this(null, 0);
    }

    /**
     * Réagit lorsque l'événement de type T est déclenché.
     * @param event L'event déclenché.
     * @return true si l'event s'est correctement exécuté, false sinon.
     */
    @Override
    public boolean onEventTriggered(CustomBlockBreakEvent event) {
        Player player = this.getEventPlayer(event);
        Block block = event.getBlock();

        player.sendMessage("Triggered");

        if (!(CustomBlock.byAlreadyPlaced(block).equals(super.target()) && !this.hasElemPlayer(player, block))) {
            return false;
        }

        player.sendMessage("Tests passed");

        this.addPlayerToElem(player, block);
        super.incrementCount();

        return super.count() >= super.amount();
    }

    @Override
    protected Player getEventPlayer(CustomBlockBreakEvent event) {
        return event.getPlayer();
    }
}
