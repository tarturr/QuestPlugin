package eu.skyrp.questpluginproject.quest.custom.itemsadder.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.custom.itemsadder.objective.IACollectQuestObjective;
import lombok.Builder;
import lombok.Singular;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class IACollectMechanic extends BaseMechanic<IACollectQuestObjective> {

    @Builder
    public IACollectMechanic(JavaPlugin main, @Singular("objective") List<IACollectQuestObjective> objectives) {
        super(main, objectives);
    }

}
