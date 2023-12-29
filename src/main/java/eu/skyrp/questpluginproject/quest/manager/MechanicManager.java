package eu.skyrp.questpluginproject.quest.manager;

import eu.skyrp.questpluginproject.lib.database.connection.BaseDatabaseConnection;
import eu.skyrp.questpluginproject.quest.common.init.Initializable;
import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class MechanicManager implements PropertyChangeListener {

    @Getter
    @Setter
    @Accessors(fluent = true)
    private List<BaseMechanic<?>> mechanics;
    private int endedMechanics;

    private final PropertyChangeSupport support;

    public MechanicManager(List<BaseMechanic<?>> mechanics, int endedMechanics) {
        this.mechanics = mechanics;
        this.endedMechanics = endedMechanics;
        this.support = new PropertyChangeSupport(this);

        this.mechanics.forEach(mechanic -> mechanic.addPropertyChangeListener(this));
    }

    public MechanicManager(List<BaseMechanic<?>> mechanics) {
        this(mechanics, 0);
    }

    public MechanicManager(int endedMechanics) {
        this(new ArrayList<>(), endedMechanics);
    }

    public static class Initializer implements Initializable<MechanicManager> {
        @Override
        public MechanicManager init(String id, ConfigurationSection section) {
            List<BaseMechanic<?>> mechanics = new ArrayList<>();

            for (String mechanicName : section.getKeys(false)) {
                ConfigurationSection currentMechanicSection = Objects.requireNonNull(section.getConfigurationSection(mechanicName));
                mechanics.addAll(Arrays.stream(new BaseMechanic.Initializer().init(id, currentMechanicSection)).toList());
            }

            return new MechanicManager(mechanics);
        }

        @Override
        public MechanicManager init(int id, BaseDatabaseConnection connection) {
            try {
                PreparedStatement statement = connection.get().prepareStatement("""
                        SELECT * FROM mechanic_manager
                        WHERE id = ?
                        """.stripIndent());

                statement.setInt(1, id);

                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    MechanicManager manager = new MechanicManager(result.getInt(3));

                    BaseDatabaseConnection.fetchIntegerListFromString(result.getString(2))
                            .forEach(mechanicId -> manager.mechanics().addAll(
                                    Arrays.stream(new BaseMechanic.Initializer().init(mechanicId, connection)).toList())
                            );

                    return manager;
                }

                return null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
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
