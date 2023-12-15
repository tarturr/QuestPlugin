package eu.skyrp.questpluginproject.quest.custom.slimefun.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.common.mechanic.MechanicType;
import eu.skyrp.questpluginproject.quest.custom.slimefun.objective.SFPlaceQuestObjective;
import lombok.Builder;
import lombok.Singular;

import java.util.List;

public class SFPlaceMechanic extends BaseMechanic<SFPlaceQuestObjective> {

    @Builder
    public SFPlaceMechanic(@Singular("objective") List<SFPlaceQuestObjective> objectives) {
        super(objectives, MechanicType.PLACE);
    }

}
