package eu.skyrp.questpluginproject.quest.custom.itemsadder.objective;

import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.Events.CustomBlockBreakEvent;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class IABreakQuestObjective extends BaseIABlockQuestObjective<CustomBlockBreakEvent> {
    /**
     * @param id         Id de la qu�te.
     * @param playerUUID UUID du joueur concern� par la qu�te.
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de qu�te.
     * @param amount     Nombre requis pour atteindre le bout de la qu�te.
     * @param plugin     Instance de la classe principale du plugin.
     */
    public IABreakQuestObjective(String id, UUID playerUUID, String targetId, int amount, JavaPlugin plugin) {
        super(id, playerUUID, targetId, amount, plugin);
    }

    /**
     * R�agit lorsque l'�v�nement de type T est d�clench�.
     *
     * @param event L'event d�clench�.
     */
    @Override
    public boolean onEventTriggered(CustomBlockBreakEvent event) {
        Player player = super.player();
        Block block = event.getBlock();

        player.sendMessage("Triggered");

        if (!(CustomBlock.byAlreadyPlaced(block).equals(super.target()) && !this.hasElemPlayer(player, block))) {
            return false;
        }

        player.sendMessage("Tests passed");

        this.addPlayerToElem(player, block);
        super.incrementCount();

        return super.getCount() >= super.getAmount();
    }

    @Override
    protected Player getEventPlayer(CustomBlockBreakEvent event) {
        return event.getPlayer();
    }
}
