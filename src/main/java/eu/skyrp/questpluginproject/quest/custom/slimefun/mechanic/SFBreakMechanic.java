package eu.skyrp.questpluginproject.quest.custom.slimefun.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.custom.slimefun.objective.SFBreakQuestObjective;
import lombok.Builder;
import lombok.Singular;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class SFBreakMechanic extends BaseMechanic<SFBreakQuestObjective> {

    @Builder
    public SFBreakMechanic(JavaPlugin main, @Singular("objective") List<SFBreakQuestObjective> objectives) {
        super(main, objectives);
    }

}
