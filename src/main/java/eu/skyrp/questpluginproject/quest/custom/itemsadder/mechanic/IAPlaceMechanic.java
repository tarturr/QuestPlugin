package eu.skyrp.questpluginproject.quest.custom.itemsadder.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.custom.itemsadder.objective.IAPlaceQuestObjective;
import lombok.Builder;
import lombok.Singular;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public class IAPlaceMechanic extends BaseMechanic<IAPlaceQuestObjective> {

    @Builder
    public IAPlaceMechanic(JavaPlugin main, UUID playerUUID, @Singular("objective") List<IAPlaceQuestObjective> objectives) {
        super(main, playerUUID, objectives);
    }

}
