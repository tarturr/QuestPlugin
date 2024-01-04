package eu.skyrp.questpluginproject.quest.custom.slimefun.objective;

import eu.skyrp.questpluginproject.quest.common.types.MechanicType;
import io.github.thebusybiscuit.slimefun4.api.events.SlimefunBlockBreakEvent;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class SFBreakQuestObjective extends BaseSFBlockQuestObjective<SlimefunBlockBreakEvent> {
    /**
     * @param targetId   Id (String) de l'objet inclus dans l'objectif de quête.
     * @param amount     Nombre requis pour atteindre le bout de la quête.
     */
    public SFBreakQuestObjective(String targetId, int amount) {
        super(SlimefunBlockBreakEvent.class, MechanicType.BREAK, targetId, amount);
    }

    public SFBreakQuestObjective() {
        this(null, 0);
    }

    /**
     * Réagit lorsque l'événement de type T est déclenché.
     * @param event L'event déclenché.
     * @return true si l'event s'est correctement exécuté, false sinon.
     */
    @Override
    public boolean onEventTriggered(SlimefunBlockBreakEvent event) {
        Player player = this.getEventPlayer(event);
        Block block = event.getBlockBroken();
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
    protected Player getEventPlayer(SlimefunBlockBreakEvent event) {
        return event.getPlayer();
    }
}
