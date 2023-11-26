package eu.skyrp.questpluginproject.quest.vanilla.objective;

import eu.skyrp.questpluginproject.quest.common.objective.cache.BaseBlockQuestObjective;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public abstract class BaseVanillaBlockQuestObjective<T extends BlockEvent> extends BaseBlockQuestObjective<T, Material> {
    /**
     * @param id         Id de la quête.
     * @param playerUUID UUID du joueur concerné par la quête.
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de quête.
     * @param amount     Nombre requis pour atteindre le bout de la quête.
     * @param plugin     Instance de la classe principale du plugin.
     */
    public BaseVanillaBlockQuestObjective(String id, UUID playerUUID, String targetId, int amount, JavaPlugin plugin) {
        super(id, playerUUID, Material.valueOf(targetId.toUpperCase()), amount, plugin);

        if (super.target() == null) {
            throw new IllegalArgumentException("The \"" + targetId + "\" item type could not be recognized.");
        }
    }

    @Override
    public void onEventTriggered(T event) {
        Player player = super.player();
        Block block = event.getBlock();

        player.sendMessage("Triggered");

        if (!(block.getType() == super.target() && !this.hasElemPlayer(player, block))) {
            return;
        }

        player.sendMessage("Tests passed");

        this.addPlayerToElem(player, block);
        super.incrementCount();

        if (super.getCount() >= super.getAmount()) {
            player.sendMessage("§a[Quests] Quête terminée !");
        }
    }
}
