package eu.skyrp.questpluginproject.quest.custom.itemsadder.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.common.types.MechanicType;
import eu.skyrp.questpluginproject.quest.custom.itemsadder.objective.IABreakQuestObjective;
import lombok.Builder;
import lombok.Singular;

import java.util.List;

public class IABreakMechanic extends BaseMechanic<IABreakQuestObjective> {

    @Builder
    public IABreakMechanic(@Singular("objective") List<IABreakQuestObjective> objectives) {
        super(objectives, MechanicType.BREAK);
    }

}
