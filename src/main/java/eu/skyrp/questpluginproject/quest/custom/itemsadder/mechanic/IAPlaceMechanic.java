package eu.skyrp.questpluginproject.quest.custom.itemsadder.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.common.types.MechanicType;
import eu.skyrp.questpluginproject.quest.custom.itemsadder.objective.IAPlaceQuestObjective;
import lombok.Builder;
import lombok.Singular;

import java.util.List;

public class IAPlaceMechanic extends BaseMechanic<IAPlaceQuestObjective> {

    public IAPlaceMechanic() {
        super(MechanicType.PLACE);
    }

    @Builder
    public IAPlaceMechanic(@Singular("objective") List<IAPlaceQuestObjective> objectives) {
        super(objectives, MechanicType.PLACE);
    }

}
