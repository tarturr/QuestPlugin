package eu.skyrp.questpluginproject.quest.common.mechanic;

import eu.skyrp.questpluginproject.quest.common.objective.BaseQuestObjective;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.Accessors;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.UUID;

@Accessors(fluent = true)
public abstract class BaseMechanic<T extends BaseQuestObjective<?, ?>> implements PropertyChangeListener {

    @Getter
    private final UUID playerUUID;
    @Getter
    @Singular("objective")
    private final List<T> objectives;
    private int endedObjectives;

    private final PropertyChangeSupport mechanicEndSupport;

    public BaseMechanic(JavaPlugin main, UUID playerUUID, List<T> objectives) {
        this.playerUUID = playerUUID;
        this.objectives = objectives;
        this.endedObjectives = 0;

        this.objectives.forEach(objective -> {
            objective.endQuestSupport().addPropertyChangeListener(this);
            main.getServer().getPluginManager().registerEvent(objective.getEventType(), objective, EventPriority.NORMAL, objective, main);
        });

        this.mechanicEndSupport = new PropertyChangeSupport(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("hasEnded")) {
            this.endedObjectives++;

            if (this.endedObjectives == this.objectives.size()) {
                this.mechanicEndSupport.firePropertyChange("objectivesAllEnded", false, true);
            }
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.mechanicEndSupport.addPropertyChangeListener(listener);
    }
}
