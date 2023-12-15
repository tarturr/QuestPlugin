package eu.skyrp.questpluginproject.quest.custom.slimefun.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.common.mechanic.MechanicType;
import eu.skyrp.questpluginproject.quest.custom.slimefun.objective.SFCollectQuestObjective;
import lombok.Builder;
import lombok.Singular;

import java.util.List;

public class SFCollectMechanic extends BaseMechanic<SFCollectQuestObjective> {

    @Builder
    public SFCollectMechanic(@Singular("objective") List<SFCollectQuestObjective> objectives) {
        super(objectives, MechanicType.COLLECT);
    }

}
