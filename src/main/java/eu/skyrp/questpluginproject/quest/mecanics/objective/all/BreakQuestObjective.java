package eu.skyrp.questpluginproject.quest.mecanics.objective.all;

import dev.lone.itemsadder.api.Events.CustomBlockBreakEvent;
import eu.skyrp.questpluginproject.quest.mecanics.objective.cache.BaseBlockQuestObjective;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class BreakQuestObjective extends BaseBlockQuestObjective<BlockBreakEvent> {
    /**
     * @param id         Id de la qu�te.
     * @param playerUUID UUID du joueur concern� par la qu�te.
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de qu�te.
     * @param amount     Nombre requis pour atteindre le bout de la qu�te.
     * @param plugin     Instance de la classe principale du plugin.
     */
    public BreakQuestObjective(String id, UUID playerUUID, String targetId, int amount, JavaPlugin plugin) {
        super(id, playerUUID, targetId, amount, plugin);
    }

    @Override
    @EventHandler
    public void onEventTriggered(BlockBreakEvent event) {
        super.onEventTriggered(event);
    }

    @EventHandler
    public void onCustomBlockBreak(CustomBlockBreakEvent event) {
        final Player player = event.getPlayer();
        final Block block = event.getBlock();

        this.onBlockTriggered(block, player);
    }

    // TODO : Faire en sorte qu'il puisse le faire aussi avec les CustomBlockBreakEvent de ItemsAdder
    @Override
    protected Player getEventPlayer(BlockBreakEvent event) {
        return event.getPlayer();
    }
}
