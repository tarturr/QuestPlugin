package eu.skyrp.questpluginproject.quest.manager;

import eu.skyrp.questpluginproject.lib.database.DatabaseColumnAutoIncrement;
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


public class MechanicManager extends DatabaseColumnAutoIncrement<MechanicManager> implements PropertyChangeListener {

    @Getter
    @Setter
    @Accessors(fluent = true)
    private List<BaseMechanic<?>> mechanics;
    private int endedMechanics;

    private final PropertyChangeSupport support;

    public MechanicManager(List<BaseMechanic<?>> mechanics, int endedMechanics) {
        super("mechanic_manager");
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
                        """);

                statement.setInt(1, id);

                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    MechanicManager manager = new MechanicManager(result.getInt(3));

                    BaseDatabaseConnection.fetchIntegerListFromString(result.getString(2))
                            .forEach(mechanicId -> new BaseMechanic.Initializer().init(mechanicId, connection));

                    return manager;
                }

                return null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void createInDatabaseImpl(BaseDatabaseConnection connection) {
        try {
            PreparedStatement statement = connection.get().prepareStatement("""
                    INSERT INTO mechanic_manager (mechanics_id, ended_mechanics)
                    VALUES (?, ?)
                    """);

            statement.setString(1, DatabaseColumnAutoIncrement.getIdsToString(this.mechanics));
            statement.setInt(2, this.endedMechanics);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the new values of the class fields in the provided database using the {@link BaseDatabaseConnection}
     * class.
     *
     * @param connection The provided database connection.
     */
    @Override
    public void update(BaseDatabaseConnection connection) {
        try {
            PreparedStatement statement = connection.get().prepareStatement("""
                    UPDATE mechanic_manager
                    SET mechanics_id = ?,
                        ended_mechanics = ?
                    WHERE id = ?
                    """);

            statement.setString(1, DatabaseColumnAutoIncrement.getIdsToString(this.mechanics));
            statement.setInt(2, this.endedMechanics);
            statement.setInt(3, super.columnId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Fetches an instance of the child class from the provided database using the {@link BaseDatabaseConnection} class.
     *
     * @param primaryKey The value of the primary key.
     * @param connection The provided database connection.
     * @return A new instance of the child class built with the query results.
     */
    @Override
    public Optional<MechanicManager> fetchFromDatabase(Integer primaryKey, BaseDatabaseConnection connection) {
        MechanicManager manager = new Initializer().init(primaryKey, connection);
        return manager == null ? Optional.empty() : Optional.of(manager);
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
