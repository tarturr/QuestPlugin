package eu.skyrp.questpluginproject.quest.mecanics;

import eu.skyrp.questpluginproject.quest.mecanics.objective.all.PlaceQuestObjective;
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
