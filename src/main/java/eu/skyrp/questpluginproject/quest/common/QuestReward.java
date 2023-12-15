package eu.skyrp.questpluginproject.quest.common;

import lombok.Builder;
import lombok.experimental.Accessors;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

@Accessors(fluent = true)
@Builder
public class QuestReward {

    private final List<QuestItem> items;
    private final double experience;
    private final double money;
    private final List<String> commands;

    public QuestReward() {
        this(null, 0.0f, 0.0f, null);
    }

    public QuestReward(List<QuestItem> items, double experience, double money, List<String> commands) {
        this.items = items;
        this.experience = experience;
        this.money = money;
        this.commands = commands;
    }

    public void giveToPlayer(Player player) {
        Inventory inventory = player.getInventory();
        this.items.forEach(item -> inventory.addItem(item.getItemStack()));
    }

    public static QuestReward createFromConfigurationSection(String name, ConfigurationSection section) {
        if (section == null) {
            return new QuestReward();
        }

        ConfigurationSection itemSection = section.getConfigurationSection("items");

        List<QuestItem> items = new ArrayList<>();
        double experience = section.getDouble("xp", 0.0);
        double money = section.getDouble("money", 0.0);
        List<String> commands = section.getStringList("money");

        boolean allNull = true;

        if (itemSection != null) {
            allNull = false;
            itemSection.getKeys(false)
                    .forEach(childSectionName -> items.add(QuestItem.createFromConfigurationSection(name, Objects.requireNonNull(itemSection.getConfigurationSection(childSectionName)))));
        }

        allNull = allNull && experience <= 0.0;
        allNull = allNull && money <= 0.0;
        allNull = allNull && commands.isEmpty();

        if (allNull) {
            throw new IllegalStateException("[QuestPlugin] No chield field has been found for the \"rewards\" field " +
                    "in the \"" + name + "\" quest configuration file.");
        }

        return new QuestReward(items, experience, money, commands);
    }
}
