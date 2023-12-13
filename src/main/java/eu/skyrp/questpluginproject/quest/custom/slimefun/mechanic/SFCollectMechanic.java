package eu.skyrp.questpluginproject.quest.custom.slimefun.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.custom.slimefun.objective.SFCollectQuestObjective;
import lombok.Builder;
import lombok.Singular;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class SFCollectMechanic extends BaseMechanic<SFCollectQuestObjective> {

    @Builder
    public SFCollectMechanic(JavaPlugin main, @Singular("objective") List<SFCollectQuestObjective> objectives) {
        super(main, objectives);
    }

}
