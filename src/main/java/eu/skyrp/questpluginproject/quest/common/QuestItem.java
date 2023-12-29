package eu.skyrp.questpluginproject.quest.common;

import dev.lone.itemsadder.api.CustomStack;
import eu.skyrp.questpluginproject.quest.common.init.ConfigurationInitializable;
import eu.skyrp.questpluginproject.quest.common.types.QuestItemType;
import eu.skyrp.questpluginproject.quest.custom.common.CustomItemNotFoundException;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public record QuestItem(String name, int amount, QuestItemType itemType) {

    public static class Initializer implements ConfigurationInitializable<QuestItem> {
        @Override
        public QuestItem init(String id, ConfigurationSection section) {
            Objects.requireNonNull(section);

            String item = section.getString("item");
            String block = section.getString("block");

            int amount = section.getInt("amount", 0);

            if (item == null && block == null) {
                throw new IllegalStateException("[QuestPlugin] No field for the block or item name has been found for " +
                        "the \"" + section.getName() + "\" item reward in the \"" + id + "\" quest configuration file.");
            }

            if (amount <= 0) {
                throw new IllegalStateException("[QuestPlugin] No amount field or an incorrect value for the amount field" +
                        " has been found for the \"" + section.getName() + "\" item reward in the \"" + id + "\" quest " +
                        "configuration file.");
            }

            String itemName = item == null ? block : item;

            return new QuestItem(itemName, amount, QuestItemType.getTypeByName(itemName));
        }
    }

    public ItemStack getItemStack() {
        return switch (this.itemType) {
            case VANILLA -> new ItemStack(Material.valueOf(this.name.toUpperCase()), this.amount);
            case ITEMSADDER -> {
                final String[] split = this.name.split(":");
                String item = split[1] + ":" + split[2];

                if (!CustomStack.isInRegistry(item)) {
                    throw new CustomItemNotFoundException("ItemsAdder", this.name);
                }

                yield CustomStack.getInstance(item).getItemStack();
            }
            case SLIMEFUN -> {
                String item = this.name.split(":")[1];
                SlimefunItem sfItem = SlimefunItem.getById(item);

                if (sfItem == null) {
                    throw new CustomItemNotFoundException("SlimeFun", this.name);
                }

                yield sfItem.getItem();
            }
        };
    }

}
