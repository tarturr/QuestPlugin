package eu.skyrp.questpluginproject.quest.vanilla.objective;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class BreakQuestObjective extends BaseVanillaBlockQuestObjective<BlockBreakEvent> {
    /**
     * @param id         Id de la qu�te.
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de qu�te.
     * @param amount     Nombre requis pour atteindre le bout de la qu�te.
     * @param plugin     Instance de la classe principale du plugin.
     */
    public BreakQuestObjective(String id, String targetId, int amount, JavaPlugin plugin) {
        super(BlockBreakEvent.class, id, targetId, amount, plugin);
    }

    @Override
    protected Player getEventPlayer(BlockBreakEvent event) {
        return event.getPlayer();
    }
}
