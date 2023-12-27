package eu.skyrp.questpluginproject.quest.vanilla.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.common.types.MechanicType;
import eu.skyrp.questpluginproject.quest.vanilla.objective.TravelQuestObjective;

import java.util.List;

public class TravelMechanic extends BaseMechanic<TravelQuestObjective> {

    public TravelMechanic(List<TravelQuestObjective> objectives) {
        super(objectives, MechanicType.TRAVEL);
    }

}
