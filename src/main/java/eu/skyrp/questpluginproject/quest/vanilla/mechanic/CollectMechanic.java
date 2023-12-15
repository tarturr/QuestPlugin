package eu.skyrp.questpluginproject.quest.vanilla.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.common.mechanic.MechanicType;
import eu.skyrp.questpluginproject.quest.vanilla.objective.CollectQuestObjective;
import lombok.Builder;
import lombok.Singular;

import java.util.List;

public class CollectMechanic extends BaseMechanic<CollectQuestObjective> {

    @Builder
    public CollectMechanic(@Singular("objective") List<CollectQuestObjective> objectives) {
        super(objectives, MechanicType.COLLECT);
    }

}
