package eu.skyrp.questpluginproject.quest.custom.itemsadder.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.common.mechanic.MechanicType;
import eu.skyrp.questpluginproject.quest.custom.itemsadder.objective.IACollectQuestObjective;
import lombok.Builder;
import lombok.Singular;

import java.util.List;

public class IACollectMechanic extends BaseMechanic<IACollectQuestObjective> {

    @Builder
    public IACollectMechanic(@Singular("objective") List<IACollectQuestObjective> objectives) {
        super(objectives, MechanicType.COLLECT);
    }

}
