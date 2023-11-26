package eu.skyrp.questpluginproject.quest.other.itemsadder.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.other.itemsadder.objective.IACollectQuestObjective;
import lombok.Builder;
import lombok.Singular;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public class IACollectMechanic extends BaseMechanic<IACollectQuestObjective> {

    @Builder
    public IACollectMechanic(JavaPlugin main, UUID playerUUID, @Singular("objective") List<IACollectQuestObjective> objectives) {
        super(main, playerUUID, objectives);
    }

}
