package eu.skyrp.questpluginproject.quest.vanilla.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.common.types.MechanicType;
import eu.skyrp.questpluginproject.quest.vanilla.objective.KillQuestObjective;
import lombok.Builder;
import lombok.Singular;

import java.util.List;

public class KillMechanic extends BaseMechanic<KillQuestObjective> {

    public KillMechanic() {
        super(MechanicType.KILL);
    }

    @Builder
    public KillMechanic(@Singular("objective") List<KillQuestObjective> objectives) {
        super(objectives, MechanicType.KILL);
    }

}
