package eu.skyrp.questpluginproject.quest.vanilla.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.vanilla.objective.CollectQuestObjective;
import lombok.Builder;
import lombok.Singular;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public class CollectMechanic extends BaseMechanic<CollectQuestObjective> {

    @Builder
    public CollectMechanic(JavaPlugin main, UUID playerUUID, @Singular("objective") List<CollectQuestObjective> objectives) {
        super(main, playerUUID, objectives);
    }

}
