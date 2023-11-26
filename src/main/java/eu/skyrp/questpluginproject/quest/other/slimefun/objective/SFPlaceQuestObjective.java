package eu.skyrp.questpluginproject.quest.other.slimefun.objective;

import io.github.thebusybiscuit.slimefun4.api.events.SlimefunBlockPlaceEvent;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class SFPlaceQuestObjective extends BaseSFBlockQuestObjective<SlimefunBlockPlaceEvent> {
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
    public void onEventTriggered(SlimefunBlockPlaceEvent event) {
        Player player = super.player();
        Block block = event.getBlockPlaced();
        SlimefunItem slimefunBlock = BlockStorage.check(block);

        player.sendMessage("Triggered");

        if (!(slimefunBlock != null && slimefunBlock.equals(super.target()) && !this.hasElemPlayer(player, block))) {
            return;
        }

        player.sendMessage("Tests passed");

        this.addPlayerToElem(player, block);
        super.incrementCount();

        if (super.getCount() >= super.getAmount()) {
            player.sendMessage("§a[Quests] Quête terminée !");
        }
    }

    @Override
    protected Player getEventPlayer(SlimefunBlockPlaceEvent event) {
        return event.getPlayer();
    }
}
