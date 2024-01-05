package eu.skyrp.questpluginproject.quest.common;

import eu.skyrp.questpluginproject.QuestPlugin;
import eu.skyrp.questpluginproject.lib.database.DatabaseColumn;
import eu.skyrp.questpluginproject.lib.database.DatabaseColumnAutoIncrement;
import eu.skyrp.questpluginproject.lib.database.connection.BaseDatabaseConnection;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Accessors(fluent = true)
public class PlayerQuests implements PropertyChangeListener, DatabaseColumn<PlayerQuests, String> {

    private final UUID uuid;
    private final List<Quest> quests;

    private final PropertyChangeSupport support;

    public PlayerQuests(UUID uuid) {
        this(uuid, new ArrayList<>());
    }

    public PlayerQuests(UUID uuid, List<Quest> quests) {
        this.uuid = uuid;
        this.quests = quests;
        this.support = new PropertyChangeSupport(this);
    }

    public boolean add(Quest quest) {
        if (this.quests.stream().noneMatch(playerQuest -> playerQuest.id().equals(quest.id()))) {
            quest.addPropertyChangeListener(this);
            this.quests.add(quest);
            return true;
        }

        return false;
    }

    public boolean remove(Quest quest) {
        if (this.quests.stream().anyMatch(playerQuest -> playerQuest.id().equals(quest.id()))) {
            this.quests.remove(quest);
            return true;
        }

        return false;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        Quest changedQuest = this.quests.stream().filter(quest -> quest.id().equals(event.getPropertyName())).findAny().orElseThrow();

        Player player = Bukkit.getPlayer(uuid);
        changedQuest.onQuestEnds(player);
        changedQuest.reward().giveToPlayer(player);

        this.quests.remove(changedQuest);

        if (changedQuest.hasNext()) {
            this.support.firePropertyChange("nextQuest", this, changedQuest.nextId());
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    public void registerAllQuests(JavaPlugin main) {
        this.quests.forEach(quest -> {
            quest.mechanicManager().registerAllMechanics(main);
        });
    }

    public void registerQuest(Quest quest, JavaPlugin main) {
        if (this.quests.stream().anyMatch(playerQuest -> playerQuest.id().equals(quest.id()))) {
            quest.mechanicManager().registerAllMechanics(main);
        }
    }

    /**
     * Creates a new column instance in the database via the provided database using the {@link BaseDatabaseConnection}
     * class.
     *
     * @param connection The provided database connection.
     */
    @Override
    public void createInDatabase(BaseDatabaseConnection connection) {
        this.quests.forEach(quest -> quest.createInDatabase(connection));

        try {
            PreparedStatement statement = connection.get().prepareStatement("""
                    INSERT INTO player_quests (uuid, quests_id)
                    VALUES (?, ?)
                    """);

            statement.setString(1, this.uuid.toString());
            statement.setString(2, DatabaseColumnAutoIncrement.getIdsToString(this.quests));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if the current class exists in the provided database using the {@link BaseDatabaseConnection} class.
     *
     * @param connection The provided database connection.
     * @return true if the check was successful, false otherwise.
     */
    @Override
    public boolean existsInDatabase(BaseDatabaseConnection connection) {
        return this.fetchFromDatabase(uuid.toString(), connection).isPresent();
    }

    /**
     * Updates the new values of the class fields in the provided database using the {@link BaseDatabaseConnection}
     * class.
     *
     * @param connection The provided database connection.
     */
    @Override
    public void update(BaseDatabaseConnection connection) {
        if (!this.existsInDatabase(connection)) {
            this.createInDatabase(connection);
        } else {
            this.quests.forEach(quest -> quest.update(connection));

            try {
                PreparedStatement statement = connection.get().prepareStatement("""
                    UPDATE player_quests
                    SET quests_id = ?
                    WHERE uuid = ?
                    """);

                statement.setString(1, DatabaseColumnAutoIncrement.getIdsToString(this.quests));
                statement.setString(2, this.uuid.toString());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
    public Optional<PlayerQuests> fetchFromDatabase(String primaryKey, BaseDatabaseConnection connection) {
        QuestPlugin.logger().info("Player quests query with id: " + primaryKey);

        try {
            PreparedStatement statement = connection.get().prepareStatement("""
                    SELECT * FROM player_quests
                    WHERE uuid = ?
            """);

            statement.setString(1, primaryKey);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return Optional.of(new PlayerQuests(
                        UUID.fromString(primaryKey),
                        BaseDatabaseConnection.fetchIntegerListFromString(result.getString(2))
                                .stream()
                                .map(id -> Optional.ofNullable(new Quest.Initializer().init(id, connection)).orElseThrow())
                                .toList()
                ));
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalArgumentException("[QuestPlugin] An error has occurred while fetching the quests of the " +
                    "player \"" + Bukkit.getOfflinePlayer(UUID.fromString(primaryKey)).getName() + "\": " + e);
        }
    }
}
