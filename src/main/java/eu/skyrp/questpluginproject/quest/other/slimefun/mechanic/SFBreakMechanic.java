package eu.skyrp.questpluginproject.quest.other.slimefun.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.other.slimefun.objective.SFBreakQuestObjective;
import lombok.Builder;
import lombok.Singular;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public class SFBreakMechanic extends BaseMechanic<SFBreakQuestObjective> {

    @Builder
    public SFBreakMechanic(JavaPlugin main, UUID playerUUID, @Singular("objective") List<SFBreakQuestObjective> objectives) {
        super(main, playerUUID, objectives);
    }

}
