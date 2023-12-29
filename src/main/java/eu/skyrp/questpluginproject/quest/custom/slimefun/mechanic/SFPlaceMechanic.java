package eu.skyrp.questpluginproject.quest.custom.slimefun.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.common.types.MechanicType;
import eu.skyrp.questpluginproject.quest.custom.slimefun.objective.SFPlaceQuestObjective;
import lombok.Builder;
import lombok.Singular;

import java.util.List;

public class SFPlaceMechanic extends BaseMechanic<SFPlaceQuestObjective> {

    public SFPlaceMechanic() {
        super(MechanicType.PLACE);
    }

    @Builder
    public SFPlaceMechanic(@Singular("objective") List<SFPlaceQuestObjective> objectives) {
        super(objectives, MechanicType.PLACE);
    }

}
