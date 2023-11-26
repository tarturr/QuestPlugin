package eu.skyrp.questpluginproject.quest.vanilla.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.vanilla.objective.PlaceQuestObjective;
import lombok.Builder;
import lombok.Singular;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public class PlaceMechanic extends BaseMechanic<PlaceQuestObjective> {

    @Builder
    public PlaceMechanic(JavaPlugin main, UUID playerUUID, @Singular("objective") List<PlaceQuestObjective> objectives) {
        super(main, playerUUID, objectives);
    }

}
