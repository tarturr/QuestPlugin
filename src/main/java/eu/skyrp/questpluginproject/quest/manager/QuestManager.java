package eu.skyrp.questpluginproject.quest.manager;

import eu.skyrp.questpluginproject.QuestPlugin;
import eu.skyrp.questpluginproject.lib.database.connection.BaseDatabaseConnection;
import eu.skyrp.questpluginproject.loader.QuestLoader;
import eu.skyrp.questpluginproject.quest.common.PlayerQuests;
import eu.skyrp.questpluginproject.quest.common.Quest;
import org.bukkit.plugin.java.JavaPlugin;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class QuestManager implements PropertyChangeListener {

    private final List<PlayerQuests> playerQuests;
    private QuestLoader questLoader;
    private final File parentFolder;

    private final JavaPlugin main;
    private final BaseDatabaseConnection connection;

    public QuestManager(JavaPlugin main, BaseDatabaseConnection connection) {
        this.playerQuests = new ArrayList<>();
        this.parentFolder = main.getDataFolder();
        this.questLoader = new QuestLoader(this.parentFolder);

        this.main = main;
        this.connection = connection;
    }

    public PlayerQuests getPlayerQuests(UUID uuid) {
        Optional<PlayerQuests> foundQuests = this.playerQuests.stream().filter(playerQuests -> playerQuests.uuid().equals(uuid)).findFirst();

        if (foundQuests.isEmpty()) {
            PlayerQuests playerQuests = new PlayerQuests(uuid);
            Optional<PlayerQuests> dbFoundQuests = playerQuests.fetchFromDatabase(uuid.toString(), this.connection);

            if (dbFoundQuests.isPresent()) {
                playerQuests = dbFoundQuests.get();
            }

            this.playerQuests.add(playerQuests);
            return playerQuests;
        }

        return foundQuests.get();
    }

    /**
     * Ajoute une nouvelle PlayerQuest.
     * @param playerUUID L'UUID du joueur.
     * @param questId L'ID de la Quest.
     * @return true si le joueur n'a pas déjà la quête et qu'elle a donc été ajoutée, false sinon.
     */
    public boolean addPlayerQuest(UUID playerUUID, String questId) {
        return this.addPlayerQuest(this.getPlayerQuests(playerUUID), questId);
    }

    private boolean addPlayerQuest(PlayerQuests quests, String questId) {
        Optional<Quest> searchedQuest = this.questLoader.getQuestById(questId);

        if (searchedQuest.isPresent()) {
            Quest quest = searchedQuest.get();
            boolean result = quests.add(quest);

            if (result) {
                quests.addPropertyChangeListener(this);
                quests.registerQuest(quest, this.main);
            }

            quests.quests().forEach(playerQuest -> {
                playerQuest.mechanicManager().mechanics().forEach(mechanic -> {
                    mechanic.objectives().forEach(objective -> {
                        QuestPlugin.logger().info("Objectif \"" + objective.columnId() + "\": " + objective.count());
                        QuestPlugin.logger().info("Target: " + objective.target());
                    });
                });
            });

            return result;
        }

        throw new IllegalArgumentException("[QuestPlugin] The provided \"" + questId + "\" does not exist or is not " +
                "loaded.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("nextQuest")) {
            PlayerQuests quests = (PlayerQuests) event.getOldValue();
            String nextQuestId = (String) event.getNewValue();

            boolean success = this.addPlayerQuest(quests, nextQuestId);

            if (!success) {
                throw new IllegalStateException("[QuestPlugin] The required \"" + nextQuestId + "\" quest is not " +
                        "loaded but is required as a next quest.");
            }
        }
    }

    public void reload() {
        this.playerQuests.clear();
        this.questLoader = new QuestLoader(this.parentFolder);
    }

    public void saveAllInDatabase() {
        this.playerQuests.forEach(playerQuests -> playerQuests.update(this.connection));
    }

    public void fetchAllFromDatabase() {
        try {
            ResultSet result = this.connection.get().prepareStatement("SELECT uuid FROM player_quests").executeQuery();

            while (result.next()) {
                String strUuid = result.getString(1);
                UUID uuid = UUID.fromString(strUuid);
                Quest.Initializer initializer = new Quest.Initializer();

                List<Quest> fullyLoadedQuests = new PlayerQuests(uuid).fetchFromDatabase(strUuid, this.connection).orElseThrow()
                        .quests().stream()
                        .map(quest -> {
                            Quest loaded = this.questLoader.getQuestById(quest.id()).orElseThrow();
                            initializer.completeByDBLoadedObject(loaded, quest);
                            return loaded;
                        })
                        .toList();

                PlayerQuests playerQuests = new PlayerQuests(uuid, fullyLoadedQuests);

                playerQuests.registerAllQuests(this.main);
                this.playerQuests.add(playerQuests);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
