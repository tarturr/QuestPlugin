package eu.skyrp.questpluginproject.quest.vanilla.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.common.types.MechanicType;
import eu.skyrp.questpluginproject.quest.vanilla.objective.ConnectQuestObjective;
import lombok.Builder;
import lombok.Singular;

import java.util.List;

public class ConnectMechanic extends BaseMechanic<ConnectQuestObjective> {

    @Builder
    public ConnectMechanic(@Singular("objective") List<ConnectQuestObjective> objectives) {
        super(objectives, MechanicType.CONNECT);
    }

}
