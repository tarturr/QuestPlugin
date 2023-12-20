package eu.skyrp.questpluginproject.quest.common;

import lombok.Setter;
import lombok.experimental.Accessors;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

@Accessors(fluent = true)
public class QuestReward {

    private final List<QuestItem> items;
    private final float experience;
    private final double money;
    private final List<String> commands;

    @Setter
    private Economy economy;

    public QuestReward() {
        this(null, 0.0f, 0.0f, null);
    }

    public QuestReward(List<QuestItem> items, float experience, double money, List<String> commands) {
        this.items = items;
        this.experience = experience;
        this.money = money;
        this.commands = commands;
    }

    public void giveToPlayer(Player player) {
        this.giveEconomy(player);
        player.sendExperienceChange(this.experience);
        this.giveItems(player);
        this.executeCommands(player);

        player.sendMessage("§a[Quests] Vous avez reçu votre récompense !\n" + this);
    }

    private void giveEconomy(Player player) {
        if (this.economy == null) {
            throw new IllegalStateException("[QuestPlugin] The vault economy instance cannot be null.");
        }

        if (!this.economy.hasAccount(player)) {
            this.economy.createPlayerAccount(player);
        }

        this.economy.depositPlayer(player, this.money);
    }

    private void executeCommands(Player player) {
        this.commands.forEach(player::performCommand);
    }

    private void giveItems(Player player) {
        Inventory inventory = player.getInventory();
        this.items.forEach(item -> inventory.addItem(item.getItemStack()));
    }

    public static QuestReward createFromConfigurationSection(String name, ConfigurationSection section) {
        if (section == null) {
            return new QuestReward();
        }

        ConfigurationSection itemSection = section.getConfigurationSection("items");

        List<QuestItem> items = new ArrayList<>();
        float experience = (float) section.getDouble("xp", 0.0);
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

    @Override
    public String toString() {
        StringBuilder reward = new StringBuilder();

        if (this.items.size() > 0) {
            reward.append("§aItems:\n");

            for (QuestItem item : this.items) {
                reward.append("  §f- §b")
                        .append(item.getItemStack().getItemMeta().displayName())
                        .append(" §7(§cx")
                        .append(item.amount())
                        .append("§7)\n");
            }
        }

        if (this.experience > 0) {
            reward.append("§aExpérience: §b")
                    .append(this.experience);
        }

        if (this.money > 0) {
            reward.append("§aMonnaie: §b")
                    .append(this.money);
        }

        return reward.toString();
    }
}
