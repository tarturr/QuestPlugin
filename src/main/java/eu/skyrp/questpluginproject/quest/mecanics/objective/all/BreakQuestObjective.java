package eu.skyrp.questpluginproject.quest.mecanics.objective.all;

import eu.skyrp.questpluginproject.api.events.BlockBreakEvent;
import eu.skyrp.questpluginproject.quest.mecanics.objective.cache.BaseBlockQuestObjective;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class BreakQuestObjective extends BaseBlockQuestObjective<BlockBreakEvent> {
    /**
     * @param id         Id de la quête.
     * @param playerUUID UUID du joueur concerné par la quête.
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de quête.
     * @param amount     Nombre requis pour atteindre le bout de la quête.
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

    // TODO : Faire en sorte qu'il puisse le faire aussi avec les CustomBlockBreakEvent de ItemsAdder
    @Override
    protected Player getEventPlayer(BlockBreakEvent event) {
        return event.getPlayer();
    }
}
