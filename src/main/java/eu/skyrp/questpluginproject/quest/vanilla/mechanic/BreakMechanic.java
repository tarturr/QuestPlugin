package eu.skyrp.questpluginproject.quest.vanilla.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.common.types.MechanicType;
import eu.skyrp.questpluginproject.quest.vanilla.objective.BreakQuestObjective;
import lombok.Builder;
import lombok.Singular;

import java.util.List;

public class BreakMechanic extends BaseMechanic<BreakQuestObjective> {

    public BreakMechanic() {
        super(MechanicType.BREAK);
    }

    @Builder
    public BreakMechanic(@Singular("objective") List<BreakQuestObjective> objectives) {
        super(objectives, MechanicType.BREAK);
    }

}
