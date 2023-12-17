package eu.skyrp.questpluginproject.quest.manager;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.common.mechanic.MechanicType;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class MechanicManager implements PropertyChangeListener {

    @Getter
    @Accessors(fluent = true)
    private final List<BaseMechanic<?>> mechanics;
    private int endedMechanics;

    private final PropertyChangeSupport support;

    public MechanicManager(List<BaseMechanic<?>> mechanics) {
        this.mechanics = mechanics;
        this.endedMechanics = 0;
        this.support = new PropertyChangeSupport(this);

        this.mechanics.forEach(mechanic -> mechanic.addPropertyChangeListener(this));
    }

    public static MechanicManager createFromConfigurationSection(String name, ConfigurationSection mechanicsSection) {
        List<BaseMechanic<?>> mechanics = new ArrayList<>();

        for (String mechanicName : mechanicsSection.getKeys(false)) {
            ConfigurationSection currentMechanicSection = Objects.requireNonNull(mechanicsSection.getConfigurationSection(mechanicName));

            Arrays.stream(MechanicType.valueOf(mechanicName.toUpperCase()).associatedMechanics()
                    .apply(name, currentMechanicSection))
                    .filter(possibleMechanic -> !possibleMechanic.objectives().isEmpty())
                    .forEach(mechanics::add);
        }

        return new MechanicManager(mechanics);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("mechanicEnded")) {
            this.endedMechanics++;

            if (this.endedMechanics == this.mechanics.size()) {
                this.support.firePropertyChange("allMechanicsEnded", false, true);
            }
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    public void registerAllMechanics(JavaPlugin main) {
        this.mechanics.forEach(mechanic -> mechanic.registerAllObjectives(main));
    }
}
