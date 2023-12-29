package eu.skyrp.questpluginproject.quest.vanilla.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.common.types.MechanicType;
import eu.skyrp.questpluginproject.quest.vanilla.objective.PlaceQuestObjective;
import lombok.Builder;
import lombok.Singular;

import java.util.List;

public class PlaceMechanic extends BaseMechanic<PlaceQuestObjective> {

    public PlaceMechanic() {
        super(MechanicType.PLACE);
    }

    @Builder
    public PlaceMechanic(@Singular("objective") List<PlaceQuestObjective> objectives) {
        super(objectives, MechanicType.PLACE);
    }

}
