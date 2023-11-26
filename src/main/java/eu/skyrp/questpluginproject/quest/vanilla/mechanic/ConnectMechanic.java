package eu.skyrp.questpluginproject.quest.vanilla.mechanic;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.vanilla.objective.ConnectQuestObjective;
import lombok.Builder;
import lombok.Singular;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public class ConnectMechanic extends BaseMechanic<ConnectQuestObjective> {

    @Builder
    public ConnectMechanic(JavaPlugin main, UUID playerUUID, @Singular("objective") List<ConnectQuestObjective> objectives) {
        super(main, playerUUID, objectives);
    }

}
