package eu.skyrp.questpluginproject.quest.manager;

import eu.skyrp.questpluginproject.lib.database.connection.BaseDatabaseConnection;
import eu.skyrp.questpluginproject.loader.QuestLoader;
import eu.skyrp.questpluginproject.quest.DailyQuest;
import eu.skyrp.questpluginproject.quest.EventQuest;
import eu.skyrp.questpluginproject.quest.OnlyOnceQuest;
import eu.skyrp.questpluginproject.quest.WeeklyQuest;
import eu.skyrp.questpluginproject.quest.common.PlayerQuests;
import eu.skyrp.questpluginproject.quest.common.Quest;
import eu.skyrp.questpluginproject.quest.common.QuestState;
import eu.skyrp.questpluginproject.quest.common.types.QuestType;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class QuestManager implements PropertyChangeListener {

    private final List<PlayerQuests> playerQuests;
    private QuestLoader questLoader;
    private final File parentFolder;
    private final Economy economy;

    private final JavaPlugin main;

    public QuestManager(JavaPlugin main) {
        this.playerQuests = new ArrayList<>();
        this.parentFolder = main.getDataFolder();
        this.questLoader = new QuestLoader(this.parentFolder);
        this.economy = this.setupEconomy(main);

        this.main = main;
    }

    public PlayerQuests getPlayerQuests(UUID uuid) {
        Optional<PlayerQuests> foundQuests = this.playerQuests.stream().filter(playerQuests -> playerQuests.uuid().equals(uuid)).findFirst();

        if (foundQuests.isEmpty()) {
            PlayerQuests playerQuests = new PlayerQuests(uuid);
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
            boolean result = quests.add(searchedQuest.get());
            quests.addPropertyChangeListener(this);
            quests.registerAllQuests(this.main, this.economy);
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

    private Economy setupEconomy(JavaPlugin main) {
        RegisteredServiceProvider<Economy> rsp = main.getServer().getServicesManager().getRegistration(Economy.class);

        if (rsp == null) {
            throw new TypeNotPresentException(
                    "net.milkbowl.vault.economy.Economy",
                    new Throwable("[QuestPlugin] The plugin cannot find the Vault dependency. Please consider adding " +
                            "the Vault plugin to the \"plugins/\" server folder!")
            );
        }

        return rsp.getProvider();
    }

    public static Quest init(YamlConfiguration conf) {
        String path = conf.getCurrentPath();

        return switch (QuestType.valueOf(Objects.requireNonNull(conf.getString("quest.type")).toUpperCase())) {
            case DAILY -> DailyQuest.createFromConfiguration(path, conf);
            case EVENT -> EventQuest.createFromConfiguration(path, conf);
            case ONLY_ONCE -> OnlyOnceQuest.createFromConfiguration(path, conf);
            case WEEKLY -> WeeklyQuest.createFromConfiguration(path, conf);
        };
    }

    public static Quest init(int id, BaseDatabaseConnection connection) {
        try {
            PreparedStatement statement = connection.get().prepareStatement("""
                        SELECT * FROM quest
                        WHERE id = ?
                        """.stripIndent()
            );

            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                QuestType type = QuestType.valueOf(result.getString(4));

                Quest quest = switch (type) {
                    case DAILY -> new DailyQuest();
                    case EVENT -> new EventQuest();
                    case ONLY_ONCE -> new OnlyOnceQuest();
                    case WEEKLY -> new WeeklyQuest();
                };

                quest.id(result.getString(2))
                        .state(QuestState.valueOf(result.getString(3)))
                        .mechanicManager(new MechanicManager()
                                .fetchFromDatabase(result.getInt(5), connection)
                                .orElseThrow()
                        );
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("[QuestPlugin] An error has occurred while fetching the quest with " +
                    "the " + id + " ID: " + e);
        }

        throw new IllegalArgumentException("[QuestPlugin] The quest with the " + id + " ID does not exist in " +
                "\"quest\" database table.");
    }
}
