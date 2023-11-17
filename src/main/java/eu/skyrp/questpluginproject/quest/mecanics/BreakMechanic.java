package eu.skyrp.questpluginproject.quest.mecanics;

import eu.skyrp.questpluginproject.quest.mecanics.objective.all.vanilla.BreakQuestObjective;
import lombok.Builder;
import lombok.Singular;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public class BreakMechanic extends BaseMechanic<BreakQuestObjective> {

    @Builder
    public BreakMechanic(JavaPlugin main, UUID playerUUID, @Singular("objective") List<BreakQuestObjective> objectives) {
        super(main, playerUUID, objectives);
    }

}
