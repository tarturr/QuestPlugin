package eu.skyrp.questpluginproject.quest.common.mechanic;

import eu.skyrp.questpluginproject.QuestPlugin;
import eu.skyrp.questpluginproject.lib.database.DatabaseColumnAutoIncrement;
import eu.skyrp.questpluginproject.lib.database.connection.BaseDatabaseConnection;
import eu.skyrp.questpluginproject.quest.common.QuestItem;
import eu.skyrp.questpluginproject.quest.common.dispatcher.MechanicDispatcher;
import eu.skyrp.questpluginproject.quest.common.init.ConfigurationInitializable;
import eu.skyrp.questpluginproject.quest.common.init.DatabaseInitializable;
import eu.skyrp.questpluginproject.quest.common.objective.BaseQuestObjective;
import eu.skyrp.questpluginproject.quest.common.objective.cache.BaseBlockQuestObjective;
import eu.skyrp.questpluginproject.quest.common.types.MechanicType;
import eu.skyrp.questpluginproject.quest.custom.itemsadder.mechanic.IABreakMechanic;
import eu.skyrp.questpluginproject.quest.custom.itemsadder.mechanic.IACollectMechanic;
import eu.skyrp.questpluginproject.quest.custom.itemsadder.mechanic.IAPlaceMechanic;
import eu.skyrp.questpluginproject.quest.custom.itemsadder.objective.IABreakQuestObjective;
import eu.skyrp.questpluginproject.quest.custom.itemsadder.objective.IACollectQuestObjective;
import eu.skyrp.questpluginproject.quest.custom.itemsadder.objective.IAPlaceQuestObjective;
import eu.skyrp.questpluginproject.quest.custom.slimefun.mechanic.SFBreakMechanic;
import eu.skyrp.questpluginproject.quest.custom.slimefun.mechanic.SFCollectMechanic;
import eu.skyrp.questpluginproject.quest.custom.slimefun.mechanic.SFPlaceMechanic;
import eu.skyrp.questpluginproject.quest.custom.slimefun.objective.SFBreakQuestObjective;
import eu.skyrp.questpluginproject.quest.custom.slimefun.objective.SFCollectQuestObjective;
import eu.skyrp.questpluginproject.quest.custom.slimefun.objective.SFPlaceQuestObjective;
import eu.skyrp.questpluginproject.quest.vanilla.mechanic.*;
import eu.skyrp.questpluginproject.quest.vanilla.objective.*;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import lombok.experimental.Accessors;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventPriority;
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
public abstract class BaseMechanic<T extends BaseQuestObjective<?, ?>> extends DatabaseColumnAutoIncrement<BaseMechanic<?>> implements PropertyChangeListener {
    @Getter
    @Singular("objective")
    private final List<T> objectives;

    @Getter
    @Setter
    private MechanicType type;
    @Getter
    @Setter
    private int endedObjectives;

    private final PropertyChangeSupport mechanicEndSupport;

    public BaseMechanic(MechanicType type) {
        this(new ArrayList<>(), type);
    }

    public BaseMechanic(List<T> objectives, MechanicType type) {
        super("mechanic");
        this.objectives = objectives;
        this.type = type;
        this.endedObjectives = 0;
        this.mechanicEndSupport = new PropertyChangeSupport(this);

        this.objectives.forEach(objective -> objective.endQuestSupport().addPropertyChangeListener(this));
    }

    public static class Initializer implements ConfigurationInitializable<List<BaseMechanic<?>>>, DatabaseInitializable<BaseMechanic<?>> {
        @Override
        public List<BaseMechanic<?>> init(String id, ConfigurationSection section) {
            return (switch (MechanicType.valueOf(section.getName().toUpperCase())) {
                case BREAK -> {
                    BreakMechanic vanilla = new BreakMechanic();
                    IABreakMechanic itemsAdder = new IABreakMechanic();
                    SFBreakMechanic slimeFun = new SFBreakMechanic();

                    for (String itemSection : section.getKeys(false)) {
                        QuestItem item = new QuestItem.Initializer().init(id, section.getConfigurationSection(itemSection));

                        switch (item.itemType()) {
                            case VANILLA -> vanilla.objectives().add(new BreakQuestObjective(item.name(), item.amount()));
                            case ITEMSADDER -> itemsAdder.objectives().add(new IABreakQuestObjective(item.name(), item.amount()));
                            case SLIMEFUN -> slimeFun.objectives().add(new SFBreakQuestObjective(item.name(), item.amount()));
                        }
                    }

                    yield List.of(vanilla, itemsAdder, slimeFun);
                }
                case COLLECT -> {
                    CollectMechanic vanilla = new CollectMechanic();
                    IACollectMechanic itemsAdder = new IACollectMechanic();
                    SFCollectMechanic slimeFun = new SFCollectMechanic();

                    for (String itemSection : section.getKeys(false)) {
                        QuestItem item = new QuestItem.Initializer().init(id, section.getConfigurationSection(itemSection));

                        switch (item.itemType()) {
                            case VANILLA -> vanilla.objectives().add(new CollectQuestObjective(item.name(), item.amount()));
                            case ITEMSADDER -> itemsAdder.objectives().add(new IACollectQuestObjective(item.name(), item.amount()));
                            case SLIMEFUN -> slimeFun.objectives().add(new SFCollectQuestObjective(item.name(), item.amount()));
                        }
                    }

                    yield List.of(vanilla, itemsAdder, slimeFun);
                }
                case CONNECT -> {
                    ConnectMechanic mechanic = new ConnectMechanic();

                    int time = section.getInt("time");
                    mechanic.objectives().add(new ConnectQuestObjective(time));

                    yield List.of(mechanic);
                }
                case KILL -> {
                    KillMechanic mechanic = new KillMechanic();

                    int amount = section.getInt("amount");
                    mechanic.objectives().add(new KillQuestObjective(Objects.requireNonNull(section.getString("type")), amount));

                    yield List.of(mechanic);
                }
                case PLACE -> {
                    PlaceMechanic vanilla = new PlaceMechanic();
                    IAPlaceMechanic itemsAdder = new IAPlaceMechanic();
                    SFPlaceMechanic slimeFun = new SFPlaceMechanic();

                    for (String itemSection : section.getKeys(false)) {
                        QuestItem item = new QuestItem.Initializer().init(id, section.getConfigurationSection(itemSection));

                        switch (item.itemType()) {
                            case VANILLA -> vanilla.objectives().add(new PlaceQuestObjective(item.name(), item.amount()));
                            case ITEMSADDER -> itemsAdder.objectives().add(new IAPlaceQuestObjective(item.name(), item.amount()));
                            case SLIMEFUN -> slimeFun.objectives().add(new SFPlaceQuestObjective(item.name(), item.amount()));
                        }
                    }

                    yield List.of(vanilla, itemsAdder, slimeFun);
                }
                case TRAVEL -> {
                    TravelMechanic mechanic = new TravelMechanic();

                    ConfigurationSection parentSection = Objects.requireNonNull(section.getParent());
                    List<String> regionIds = Objects.requireNonNull(parentSection.getStringList(section.getName()));
                    mechanic.objectives().add(new TravelQuestObjective(regionIds));

                    yield List.of(mechanic);
                }
                default -> throw new NotImplementedException("[QuestPlugin] You cannot define a category other than " +
                        "the expected ones!");
            }).stream()
                    .filter(mechanic -> mechanic.objectives().size() > 0)
                    .collect(Collectors.toList());
        }

        @Override
        public BaseMechanic<?> init(int id, BaseDatabaseConnection connection) {
            QuestPlugin.logger().info("Mechanic query with id: " + id);

            try {
                PreparedStatement statement = connection.get().prepareStatement("""
                        SELECT * FROM mechanic
                        WHERE id = ?
                        """);

                statement.setInt(1, id);

                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    BaseMechanic<?> mechanic = new MechanicDispatcher().dispatch(result.getString(3))
                            .endedObjectives(result.getInt(4))
                            .objectives(
                                    BaseDatabaseConnection.fetchIntegerListFromString(result.getString(2))
                                            .stream()
                                            .map(objectiveId -> Optional.ofNullable(new BaseQuestObjective.Initializer().init(objectiveId, connection)).orElseThrow())
                                            .toList()
                            );

                    mechanic.columnId(id);
                    return mechanic;
                }

                return null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void completeByDBLoadedObject(BaseMechanic<?> src, BaseMechanic<?> loaded) {
            src.columnId(loaded.columnId());

            BaseQuestObjective.Initializer initializer = new BaseQuestObjective.Initializer();

            for (int i = 0; i < src.objectives().size(); i++) {
                initializer.completeByDBLoadedObject(src.objectives().get(i), loaded.objectives().get(i));
            }

            src.type(loaded.type());
            src.endedObjectives(loaded.endedObjectives());
        }
    }

    @Override
    protected void createInDatabaseImpl(BaseDatabaseConnection connection) {
        this.objectives.forEach(objective -> objective.createInDatabase(connection));

        try {
            PreparedStatement statement = connection.get().prepareStatement("""
                    INSERT INTO mechanic (objectives_id, type, ended_objectives)
                    VALUES (?, ?, ?)
                    """);

            statement.setString(1, DatabaseColumnAutoIncrement.getIdsToString(this.objectives));
            statement.setString(2, this.type.toString());
            statement.setInt(3, this.endedObjectives);
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
        this.objectives.forEach(objective -> objective.update(connection));

        try {
            PreparedStatement statement = connection.get().prepareStatement("""
                    UPDATE mechanic
                    SET objectives_id = ?,
                        ended_objectives = ?
                    WHERE id = ?
                    """);

            statement.setString(1, DatabaseColumnAutoIncrement.getIdsToString(this.objectives));
            statement.setInt(2, this.endedObjectives);
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
    public Optional<BaseMechanic<?>> fetchFromDatabase(Integer primaryKey, BaseDatabaseConnection connection) {
        return Optional.ofNullable(new Initializer().init(primaryKey, connection));
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("hasEnded")) {
            this.endedObjectives++;

            if (this.endedObjectives == this.objectives.size()) {
                this.mechanicEndSupport.firePropertyChange("mechanicEnded", false, true);
            }
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.mechanicEndSupport.addPropertyChangeListener(listener);
    }

    public void registerAllObjectives(JavaPlugin main) {
        this.objectives.forEach(objective -> {
            main.getServer().getPluginManager().registerEvent(objective.eventType(), objective, EventPriority.NORMAL, objective, main);

            if (objective instanceof BaseBlockQuestObjective<?,?> blockObjective) {
                blockObjective.initMetaData(main);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <U extends BaseQuestObjective<?, ?>> BaseMechanic<T> objectives(List<U> objectives) {
        objectives.forEach(objective -> this.objectives.add((T) objective));
        return this;
    }
}
