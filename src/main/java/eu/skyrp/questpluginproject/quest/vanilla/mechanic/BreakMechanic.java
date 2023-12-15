package eu.skyrp.questpluginproject.quest.vanilla.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.common.mechanic.MechanicType;
import eu.skyrp.questpluginproject.quest.vanilla.objective.BreakQuestObjective;
import lombok.Builder;
import lombok.Singular;

import java.util.List;

public class BreakMechanic extends BaseMechanic<BreakQuestObjective> {

    @Builder
    public BreakMechanic(@Singular("objective") List<BreakQuestObjective> objectives) {
        super(objectives, MechanicType.BREAK);
    }

}
