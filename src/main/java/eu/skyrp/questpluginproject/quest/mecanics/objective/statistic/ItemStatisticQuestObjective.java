package eu.skyrp.questpluginproject.quest.mecanics.objective.statistic;

import eu.skyrp.questpluginproject.quest.mecanics.objective.Countable;
import org.bukkit.Statistic;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class ItemStatisticQuestObjective extends BaseStatisticQuestObjective<ItemStack> {

    public ItemStatisticQuestObjective(String id, UUID player, Statistic type, ItemStack target, int amount) {
        super(id, player, type, target, amount);
        super.amount += this.getCount() - 1;
    }

    /**
     * @return le nombre de la stat
     * @see Countable#getCount()
     */
    @Override
    public final int getCount() {
        return super.player().getStatistic(this.type, this.target.getType());
    }

}
