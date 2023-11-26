package eu.skyrp.questpluginproject.quest.other.itemsadder.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.other.itemsadder.objective.IABreakQuestObjective;
import lombok.Builder;
import lombok.Singular;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public class IABreakMechanic extends BaseMechanic<IABreakQuestObjective> {

    @Builder
    public IABreakMechanic(JavaPlugin main, UUID playerUUID, @Singular("objective") List<IABreakQuestObjective> objectives) {
        super(main, playerUUID, objectives);
    }

}
