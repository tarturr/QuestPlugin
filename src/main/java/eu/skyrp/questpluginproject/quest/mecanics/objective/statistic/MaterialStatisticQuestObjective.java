package eu.skyrp.questpluginproject.quest.mecanics.objective.statistic;

import org.bukkit.Material;
import org.bukkit.Statistic;

import java.util.UUID;

public class MaterialStatisticQuestObjective extends BaseStatisticQuestObjective<Material> {

    public MaterialStatisticQuestObjective(String id, UUID player, Statistic type, Material target, int amount) {
        super(id, player, type, target, amount);
        super.amount += this.getCount() - 1;
    }

    @Override
    public final int getCount() {
        return super.player().getStatistic(this.type, this.target);
    }

}
