package eu.skyrp.questpluginproject.quest.custom.slimefun.objective;

import eu.skyrp.questpluginproject.quest.common.types.MechanicType;
import io.github.thebusybiscuit.slimefun4.api.events.SlimefunBlockPlaceEvent;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class SFPlaceQuestObjective extends BaseSFBlockQuestObjective<SlimefunBlockPlaceEvent> {
    /**
     * @param id         Id de la qu�te.
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de qu�te.
     * @param amount     Nombre requis pour atteindre le bout de la qu�te.
     */
    public SFPlaceQuestObjective(String id, String targetId, int amount) {
        super(SlimefunBlockPlaceEvent.class, MechanicType.PLACE, id, targetId, amount);
    }

    public SFPlaceQuestObjective() {
        this(null, null, 0);
    }

    /**
     * R�agit lorsque l'�v�nement de type T est d�clench�.
     * @param event L'event d�clench�.
     * @return true si l'event s'est correctement ex�cut�, false sinon.
     */
    @Override
    public boolean onEventTriggered(SlimefunBlockPlaceEvent event) {
        Player player = this.getEventPlayer(event);
        Block block = event.getBlockPlaced();
        SlimefunItem slimefunBlock = BlockStorage.check(block);

        player.sendMessage("Triggered");

        if (!(slimefunBlock != null && slimefunBlock.equals(super.target()) && !this.hasElemPlayer(player, block))) {
            return false;
        }

        player.sendMessage("Tests passed");

        this.addPlayerToElem(player, block);
        super.incrementCount();

        return super.count() >= super.amount();
    }

    @Override
    protected Player getEventPlayer(SlimefunBlockPlaceEvent event) {
        return event.getPlayer();
    }
}
