package eu.skyrp.questpluginproject.quest.manager;

import eu.skyrp.questpluginproject.loader.QuestLoader;
import eu.skyrp.questpluginproject.quest.common.PlayerQuests;
import eu.skyrp.questpluginproject.quest.common.Quest;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
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
}
