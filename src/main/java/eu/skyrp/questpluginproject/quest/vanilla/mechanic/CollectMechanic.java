package eu.skyrp.questpluginproject.quest.vanilla.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.vanilla.objective.CollectQuestObjective;
import lombok.Builder;
import lombok.Singular;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class CollectMechanic extends BaseMechanic<CollectQuestObjective> {

    @Builder
    public CollectMechanic(JavaPlugin main, @Singular("objective") List<CollectQuestObjective> objectives) {
        super(main, objectives);
    }

}
