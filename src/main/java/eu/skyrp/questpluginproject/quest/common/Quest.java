package eu.skyrp.questpluginproject.quest.common;

import eu.skyrp.questpluginproject.QuestPlugin;
import eu.skyrp.questpluginproject.lib.database.DatabaseColumnAutoIncrement;
import eu.skyrp.questpluginproject.lib.database.connection.BaseDatabaseConnection;
import eu.skyrp.questpluginproject.quest.common.dispatcher.QuestDispatcher;
import eu.skyrp.questpluginproject.quest.common.init.Initializable;
import eu.skyrp.questpluginproject.quest.common.types.QuestType;
import eu.skyrp.questpluginproject.quest.manager.MechanicManager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@Accessors(fluent = true)
@NoArgsConstructor
public abstract class Quest extends DatabaseColumnAutoIncrement<Quest> implements PropertyChangeListener {

    private String id;
    @Setter(AccessLevel.NONE)
    private QuestState state;

    private QuestType type;
    private String name;
    private List<String> lore;
    private QuestReward reward;
    private String nextId;
    @Setter(AccessLevel.NONE)
    private MechanicManager mechanicManager;

    private Initializer initializer;
    private PropertyChangeSupport support;

    public Quest(QuestType type, String id, String name, List<String> lore, QuestReward reward, MechanicManager mechanicManager, Initializer initializer) {
        this(type, id, name, lore, reward, mechanicManager, (String) null, initializer);
    }

    public Quest(QuestType type, String id, String name, List<String> lore, QuestReward reward, MechanicManager mechanicManager, String nextId, Initializer initializer) {
        super("quest");
        this.id = id;
        this.state = QuestState.NOT_STARTED;

        this.type = type;
        this.name = name;
        this.lore = lore;
        this.reward = reward;
        this.mechanicManager = mechanicManager;
        this.mechanicManager.addPropertyChangeListener(this);
        this.nextId = nextId;
        this.support = new PropertyChangeSupport(this);
        this.initializer = initializer;
    }

    public Quest(QuestType type, String id, String name, List<String> lore, QuestReward reward, MechanicManager mechanicManager, Quest next, Initializer initializer) {
        this(type, id, name, lore, reward, mechanicManager, next.id(), initializer);
    }

    public static class Initializer implements Initializable<Quest> {

        private final QuestDispatcher dispatcher;

        @Setter
        @Accessors(fluent = true)
        private Quest quest;

        public Initializer() {
            this.dispatcher = new QuestDispatcher();
        }

        @Override
        public Quest init(String id, ConfigurationSection section) {
            Quest quest = this.quest == null ? this.dispatcher.dispatch(Objects.requireNonNull(section.getString("type"))) : this.quest;

            quest.id(Objects.requireNonNull(section.getString("id")));
            quest.type(QuestType.valueOf(Objects.requireNonNull(section.getString("type")).toUpperCase()));
            quest.name(Objects.requireNonNull(section.getString("name")));
            quest.lore(section.getStringList("lore"));
            quest.state(QuestState.NOT_STARTED);
            quest.reward(new QuestReward.Initializer().init(id, section.getConfigurationSection("rewards")));
            quest.mechanicManager(new MechanicManager.Initializer().init(id, Objects.requireNonNull(Objects.requireNonNull(section.getParent()).getConfigurationSection("mechanics"))));
            quest.table("quest");
            quest.support(new PropertyChangeSupport(quest));

            return quest;
        }

        @Override
        public Quest init(int id, BaseDatabaseConnection connection) {
            QuestPlugin.logger().info("Quest query with id: " + id);

            try {
                PreparedStatement statement = connection.get().prepareStatement("""
                        SELECT * FROM quest
                        WHERE id = ?
                        """
                );

                statement.setInt(1, id);

                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    Quest quest = this.dispatcher.dispatch(result.getString(4));

                    return quest.id(result.getString(2))
                            .state(QuestState.valueOf(result.getString(3)))
                            .mechanicManager(Optional.ofNullable(new MechanicManager.Initializer().init(result.getInt(5), connection)).orElseThrow());
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException("[QuestPlugin] An error has occurred while fetching the quest with " +
                        "the " + id + " ID: " + e);
            }

            throw new IllegalArgumentException("[QuestPlugin] The quest with the " + id + " ID does not exist in " +
                    "\"quest\" database table.");
        }

        public Quest init(YamlConfiguration conf) {
            ConfigurationSection section = Objects.requireNonNull(conf.getConfigurationSection("quest"));
            String id = Objects.requireNonNull(section.getString("id"));
            Quest quest = this.dispatcher.dispatch(Objects.requireNonNull(section.getString("type")));

            return quest.initializer().quest(quest).init(id, section);
        }

    }

    public abstract void onQuestEnds(Player player);

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("allMechanicsEnded")) {
            this.state(QuestState.ENDED);
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    @Override
    protected void createInDatabaseImpl(BaseDatabaseConnection connection) {
        this.mechanicManager.createInDatabase(connection);

        try {
            PreparedStatement statement = connection.get().prepareStatement("""
                    INSERT INTO quest (str_id, state, type, mechanic_manager)
                    VALUES (?, ?, ?, ?)
                    """);

            statement.setString(1, this.id);
            statement.setString(2, this.state.toString());
            statement.setString(3, this.type.toString());
            statement.setInt(4, this.mechanicManager.columnId());
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
        this.mechanicManager.update(connection);

        try {
            PreparedStatement statement = connection.get().prepareStatement("""
                    UPDATE quest
                    SET state = ?
                    WHERE id = ?
                    """);

            statement.setString(1, this.state.toString());
            statement.setInt(2, super.columnId());
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
    public Optional<Quest> fetchFromDatabase(Integer primaryKey, BaseDatabaseConnection connection) {
        return Optional.ofNullable(new Quest.Initializer().init(primaryKey, connection));
    }

    public boolean hasNext() {
        return this.nextId != null;
    }

    public Quest mechanicManager(MechanicManager mechanicManager) {
        mechanicManager.addPropertyChangeListener(this);
        this.mechanicManager = mechanicManager;
        return this;
    }

    public Quest state(QuestState state) {
        QuestState old = this.state;
        this.state = state;
        this.support.firePropertyChange(this.id, old, this.state);
        return this;
    }
}
