package eu.skyrp.questpluginproject.quest.mecanics;

import eu.skyrp.questpluginproject.quest.mecanics.objective.BaseQuestObjective;
import lombok.Getter;
import lombok.Singular;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public abstract class BaseMechanic<T extends BaseQuestObjective<?, ?>> {

    private final JavaPlugin main;
    @Getter
    private final UUID playerUUID;
    @Getter
    @Singular("objective")
    private final List<T> objectives;

    public BaseMechanic(JavaPlugin main, UUID playerUUID, List<T> objectives) {
        this.main = main;
        this.playerUUID = playerUUID;
        this.objectives = objectives;
        this.objectives.forEach(objective -> main.getServer().getPluginManager().registerEvents(objective, main));
    }

}
