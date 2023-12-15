package eu.skyrp.questpluginproject.quest.manager;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import eu.skyrp.questpluginproject.quest.common.mechanic.MechanicType;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class MechanicManager {

    private final List<BaseMechanic<?>> mechanics;

    public MechanicManager(List<BaseMechanic<?>> mechanics) {
        this.mechanics = mechanics;
    }

    public List<BaseMechanic<?>> getAllMechanics() {
        return this.mechanics;
    }

    public static MechanicManager createFromConfigurationSection(String name, ConfigurationSection mechanicsSection) {
        List<BaseMechanic<?>> mechanics = new ArrayList<>();

        for (String mechanicName : mechanicsSection.getKeys(false)) {
            ConfigurationSection currentMechanicSection = Objects.requireNonNull(mechanicsSection.getConfigurationSection(mechanicName));

            Arrays.stream(MechanicType.valueOf(mechanicName.toUpperCase()).associatedMechanics()
                    .apply(name, currentMechanicSection)).filter(possibleMechanic -> !possibleMechanic.objectives().isEmpty())
                    .forEach(mechanics::add);
        }

        return new MechanicManager(mechanics);
    }

}
