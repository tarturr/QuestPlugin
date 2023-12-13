package eu.skyrp.questpluginproject.quest.custom.itemsadder.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.custom.itemsadder.objective.IAPlaceQuestObjective;
import lombok.Builder;
import lombok.Singular;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class IAPlaceMechanic extends BaseMechanic<IAPlaceQuestObjective> {

    @Builder
    public IAPlaceMechanic(JavaPlugin main, @Singular("objective") List<IAPlaceQuestObjective> objectives) {
        super(main, objectives);
    }

}
