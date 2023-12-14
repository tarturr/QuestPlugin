package eu.skyrp.questpluginproject.inventories;

import fr.mrmicky.fastinv.FastInv;
import org.bukkit.event.inventory.InventoryClickEvent;

public class QuestInventory extends FastInv {

    public QuestInventory() {
        super(54);
    }

    @Override
    protected void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }
}
