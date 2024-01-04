package eu.skyrp.questpluginproject.quest.custom.itemsadder.objective;

import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.Events.CustomBlockPlaceEvent;
import eu.skyrp.questpluginproject.quest.common.types.MechanicType;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class IAPlaceQuestObjective extends BaseIABlockQuestObjective<CustomBlockPlaceEvent> {
    /**
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de qu�te.
     * @param amount     Nombre requis pour atteindre le bout de la qu�te.
     */
    public IAPlaceQuestObjective(String targetId, int amount) {
        super(CustomBlockPlaceEvent.class, MechanicType.PLACE, targetId, amount);
    }

    public IAPlaceQuestObjective() {
        this(null, 0);
    }

    /**
     * R�agit lorsque l'�v�nement de type T est d�clench�.
     * @param event L'event d�clench�.
     * @return true si l'event s'est correctement ex�cut�, false sinon.
     */
    @Override
    @EventHandler
    public boolean onEventTriggered(CustomBlockPlaceEvent event) {
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
    protected Player getEventPlayer(CustomBlockPlaceEvent event) {
        return event.getPlayer();
    }
}
