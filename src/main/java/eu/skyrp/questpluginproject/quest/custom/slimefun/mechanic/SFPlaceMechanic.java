package eu.skyrp.questpluginproject.quest.custom.slimefun.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.custom.slimefun.objective.SFPlaceQuestObjective;
import lombok.Builder;
import lombok.Singular;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public class SFPlaceMechanic extends BaseMechanic<SFPlaceQuestObjective> {

    @Builder
    public SFPlaceMechanic(JavaPlugin main, UUID playerUUID, @Singular("objective") List<SFPlaceQuestObjective> objectives) {
        super(main, playerUUID, objectives);
    }

}
