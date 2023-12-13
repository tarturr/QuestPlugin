package eu.skyrp.questpluginproject.quest.custom.itemsadder.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.custom.itemsadder.objective.IABreakQuestObjective;
import lombok.Builder;
import lombok.Singular;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class IABreakMechanic extends BaseMechanic<IABreakQuestObjective> {

    @Builder
    public IABreakMechanic(JavaPlugin main, @Singular("objective") List<IABreakQuestObjective> objectives) {
        super(main, objectives);
    }

}
