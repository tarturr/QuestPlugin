package eu.skyrp.questpluginproject.quest.manager;

import eu.skyrp.questpluginproject.QuestPlugin;
import eu.skyrp.questpluginproject.lib.database.DatabaseColumnAutoIncrement;
import eu.skyrp.questpluginproject.lib.database.connection.BaseDatabaseConnection;
import eu.skyrp.questpluginproject.quest.common.init.Initializable;
import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import lombok.AccessLevel;
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
import java.util.stream.Collectors;


@Accessors(fluent = true)
public class MechanicManager extends DatabaseColumnAutoIncrement<MechanicManager> implements PropertyChangeListener {

    @Getter
    @Setter
    private List<BaseMechanic<?>> mechanics;
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
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
                mechanics.addAll(new BaseMechanic.Initializer().init(id, currentMechanicSection));
            }

            return new MechanicManager(mechanics);
        }

        @Override
        public MechanicManager init(int id, BaseDatabaseConnection connection) {
            QuestPlugin.logger().info("Mechanic manager query with id: " + id);

            try {
                PreparedStatement statement = connection.get().prepareStatement("""
                        SELECT * FROM mechanic_manager
                        WHERE id = ?
                        """);

                statement.setInt(1, id);

                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    MechanicManager manager = new MechanicManager(result.getInt(3));
                    manager.columnId(id);
                    manager.mechanics(
                            BaseDatabaseConnection.fetchIntegerListFromString(result.getString(2))
                                    .stream()
                                    .map(mechanicId -> Optional.ofNullable(new BaseMechanic.Initializer().init(mechanicId, connection)).orElseThrow())
                                    .collect(Collectors.toList())
                    );

                    return manager;
                }

                return null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void completeByDBLoadedObject(MechanicManager src, MechanicManager loaded) {
            src.columnId(loaded.columnId());

            BaseMechanic.Initializer initializer = new BaseMechanic.Initializer();

            for (int i = 0; i < src.mechanics.size(); i++) {
                initializer.completeByDBLoadedObject(src.mechanics().get(i), loaded.mechanics().get(i));
            }

            src.endedMechanics(src.endedMechanics());
        }
    }

    @Override
    protected void createInDatabaseImpl(BaseDatabaseConnection connection) {
        this.mechanics.forEach(mechanic -> mechanic.createInDatabase(connection));

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
    protected void updateImpl(BaseDatabaseConnection connection) {
        this.mechanics.forEach(mechanic -> mechanic.update(connection));

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
        return Optional.ofNullable(new Initializer().init(primaryKey, connection));
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
