package eu.skyrp.questpluginproject.quest.vanilla.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.common.mechanic.MechanicType;
import eu.skyrp.questpluginproject.quest.vanilla.objective.PlaceQuestObjective;
import lombok.Builder;
import lombok.Singular;

import java.util.List;

public class PlaceMechanic extends BaseMechanic<PlaceQuestObjective> {

    @Builder
    public PlaceMechanic(@Singular("objective") List<PlaceQuestObjective> objectives) {
        super(objectives, MechanicType.PLACE);
    }

}
