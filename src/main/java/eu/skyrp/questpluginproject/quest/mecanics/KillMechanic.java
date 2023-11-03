package eu.skyrp.questpluginproject.quest.mecanics;

import eu.skyrp.questpluginproject.quest.mecanics.objective.all.KillQuestObjective;
import lombok.Builder;
import lombok.Singular;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public class KillMechanic extends BaseMechanic<KillQuestObjective> {
    @Builder
    public KillMechanic(JavaPlugin main, UUID playerUUID, @Singular("objective") List<KillQuestObjective> objectives) {
        super(main, playerUUID, objectives);
    }

}
