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

@Accessors(fluent = true)
public abstract class BaseMechanic<T extends BaseQuestObjective<?, ?>> implements PropertyChangeListener {
    @Getter
    @Singular("objective")
    private final List<T> objectives;

    @Getter
    private final MechanicType type;
    private int endedObjectives;

    private final PropertyChangeSupport mechanicEndSupport;

    public BaseMechanic(List<T> objectives, MechanicType type) {
        this.objectives = objectives;
        this.type = type;
        this.endedObjectives = 0;
        this.mechanicEndSupport = new PropertyChangeSupport(this);

        this.objectives.forEach(objective -> objective.endQuestSupport().addPropertyChangeListener(this));
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

    public void registerAllObjectives(JavaPlugin main) {
        this.objectives.forEach(objective -> main.getServer().getPluginManager().registerEvent(objective.getEventType(), objective, EventPriority.NORMAL, objective, main));
    }
}
