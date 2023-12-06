package eu.skyrp.questpluginproject.quest.common;

import dev.lone.itemsadder.api.CustomStack;
import eu.skyrp.questpluginproject.quest.custom.common.CustomObjectFinder;
import eu.skyrp.questpluginproject.quest.custom.common.MalformedCustomItemIDException;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import lombok.Builder;
import lombok.Singular;
import lombok.experimental.Accessors;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Accessors(fluent = true)
@Builder
public record QuestReward(@Singular("item") Map<String, Integer> items, float experience, float money, @Singular("command") List<String> commands) {

    public void giveToPlayer(Player player) {
        Inventory inventory = player.getInventory();

        for (String itemId : this.items.keySet()) {
            if (!itemId.contains(":")) {
                inventory.addItem(new ItemStack(Material.valueOf(itemId.toUpperCase()), this.items.get(itemId)));
            } else {
                String apiName = itemId.split(":")[0].toLowerCase();

                if (apiName.equals("ai") || apiName.equals("itemsadder")) {
                    inventory.addItem(CustomObjectFinder.getById(itemId, apiName, "ia", CustomStack::isInRegistry, CustomStack::getInstance).getItemStack());
                } else if (apiName.equals("sf") || apiName.equals("slimefun")) {
                    inventory.addItem(CustomObjectFinder.getById(itemId, apiName, "sf", (item) -> SlimefunItem.getById(item) != null, SlimefunItem::getById).getItem());
                } else {
                    throw new MalformedCustomItemIDException("itemsadder/slimefun", itemId, "ia/sf");
                }
            }
        }
    }

}
