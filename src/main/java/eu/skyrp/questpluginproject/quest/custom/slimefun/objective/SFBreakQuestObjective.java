package eu.skyrp.questpluginproject.quest.custom.slimefun.objective;

import io.github.thebusybiscuit.slimefun4.api.events.SlimefunBlockBreakEvent;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class SFBreakQuestObjective extends BaseSFBlockQuestObjective<SlimefunBlockBreakEvent> {
    /**
     * @param id         Id de la quête.
     * @param playerUUID UUID du joueur concerné par la quête.
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de quête.
     * @param amount     Nombre requis pour atteindre le bout de la quête.
     * @param plugin     Instance de la classe principale du plugin.
     */
    public SFBreakQuestObjective(String id, UUID playerUUID, String targetId, int amount, JavaPlugin plugin) {
        super(SlimefunBlockBreakEvent.class, id, playerUUID, targetId, amount, plugin);
    }

    @Override
    public boolean onEventTriggered(SlimefunBlockBreakEvent event) {
        Player player = super.player();
        Block block = event.getBlockBroken();
        SlimefunItem slimefunBlock = BlockStorage.check(block);

        player.sendMessage("Triggered");

        if (!(slimefunBlock != null && slimefunBlock.equals(super.target()) && !this.hasElemPlayer(player, block))) {
            return false;
        }

        player.sendMessage("Tests passed");

        this.addPlayerToElem(player, block);
        super.incrementCount();

        return super.getCount() >= super.getAmount();
    }

    @Override
    protected Player getEventPlayer(SlimefunBlockBreakEvent event) {
        return event.getPlayer();
    }
}
