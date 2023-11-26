package eu.skyrp.questpluginproject.quest.other.slimefun.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.other.slimefun.objective.SFCollectQuestObjective;
import lombok.Builder;
import lombok.Singular;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public class SFCollectMechanic extends BaseMechanic<SFCollectQuestObjective> {

    @Builder
    public SFCollectMechanic(JavaPlugin main, UUID playerUUID, @Singular("objective") List<SFCollectQuestObjective> objectives) {
        super(main, playerUUID, objectives);
    }

}
