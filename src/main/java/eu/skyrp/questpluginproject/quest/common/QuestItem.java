package eu.skyrp.questpluginproject.quest.common;

import dev.lone.itemsadder.api.CustomStack;
import eu.skyrp.questpluginproject.quest.custom.common.CustomItemNotFoundException;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public record QuestItem(String name, int amount, QuestItemType itemType) {

    public static QuestItem createFromConfigurationSection(String name, ConfigurationSection section) {
        Objects.requireNonNull(section);

        String item = section.getString("item");
        String block = section.getString("block");

        int amount = section.getInt("amount", 0);

        if (item == null && block == null) {
            throw new IllegalStateException("[QuestPlugin] No field for the block or item name has been found for " +
                    "the \"" + section.getName() + "\" item reward in the \"" + name + "\" quest configuration file.");
        }

        if (amount <= 0) {
            throw new IllegalStateException("[QuestPlugin] No amount field or an incorrect value for the amount field" +
                    " has been found for the \"" + section.getName() + "\" item reward in the \"" + name + "\" quest " +
                    "configuration file.");
        }

        return new QuestItem(item, amount, QuestItemType.getTypeByName(item == null ? block : item));
    }

    public ItemStack getItemStack() {
        return switch (this.itemType) {
            case VANILLA -> new ItemStack(Material.valueOf(this.name.toUpperCase()), this.amount);
            case ITEMSADDER -> {
                String item = this.name.split(":")[1];

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
