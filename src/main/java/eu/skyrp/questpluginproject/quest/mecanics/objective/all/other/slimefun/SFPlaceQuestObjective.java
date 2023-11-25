package eu.skyrp.questpluginproject.quest.mecanics.objective.all.other.slimefun;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class SFPlaceQuestObjective extends BaseSFBlockQuestObjective<BlockPlaceEvent> {
    /**
     * @param id         Id de la quête.
     * @param playerUUID UUID du joueur concerné par la quête.
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de quête.
     * @param amount     Nombre requis pour atteindre le bout de la quête.
     * @param plugin     Instance de la classe principale du plugin.
     */
    public SFPlaceQuestObjective(String id, UUID playerUUID, String targetId, int amount, JavaPlugin plugin) {
        super(id, playerUUID, targetId, amount, plugin);
    }

    @Override
    @EventHandler
    public void onEventTriggered(BlockPlaceEvent event) {
        super.onEventTriggered(event);
    }

    @Override
    protected Player getEventPlayer(BlockPlaceEvent event) {
        return event.getPlayer();
    }
}
