package eu.skyrp.questpluginproject.quest.common;

import eu.skyrp.questpluginproject.quest.manager.MechanicManager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@Accessors(fluent = true)
@NoArgsConstructor
public abstract class Quest {

    private String id;
    @Setter(AccessLevel.PROTECTED)
    private QuestState state;

    private QuestType type;
    private String name;
    private List<String> lore;
    private QuestReward reward;
    private String nextId;
    private MechanicManager mechanicManager;

    public Quest(QuestType type, String id, String name, List<String> lore, QuestReward reward, MechanicManager mechanicManager) {
        this(type, id, name, lore, reward, mechanicManager, (String) null);
    }

    public Quest(QuestType type, String id, String name, List<String> lore, QuestReward reward, MechanicManager mechanicManager, String nextId) {
        this.id = id;
        this.state = QuestState.NOT_STARTED;

        this.type = type;
        this.name = name;
        this.lore = lore;
        this.reward = reward;
        this.mechanicManager = mechanicManager;
        this.nextId = nextId;
    }

    public Quest(QuestType type, String id, String name, List<String> lore, QuestReward reward, MechanicManager mechanicManager, Quest next) {
        this(type, id, name, lore, reward, mechanicManager, next.id());
    }

    public static void initQuestFromConfiguration(Quest quest, YamlConfiguration conf) {
        ConfigurationSection section = Objects.requireNonNull(conf.getConfigurationSection("quest"));

        quest.id(Objects.requireNonNull(section.getString("id")))
                .type(QuestType.valueOf(Objects.requireNonNull(section.getString("type")).toUpperCase()))
                .name(Objects.requireNonNull(section.getString("name")))
                .lore(section.getStringList("lore"))
                .reward(QuestReward.createFromConfigurationSection(conf.getCurrentPath(), section.getConfigurationSection("rewards")))
                .mechanicManager(MechanicManager.createFromConfigurationSection(conf.getCurrentPath(), Objects.requireNonNull(conf.getConfigurationSection("mechanics"))));
    }

    public abstract void onQuestEnds(Player player);

    public boolean hasNext() {
        return this.nextId != null;
    }

    public QuestType getType() {
        return type;
    }

    public Quest next(List<Quest> initializedQuests) {
        if (!this.hasNext()) {
            throw new IllegalStateException("[QuestPlugin] Trying to access to the unexisting next quest of the " +
                    "quest with the \"" + this.id + "\" ID.");
        }

        Optional<Quest> nextQuest = initializedQuests.stream().filter(quest -> quest.id().equals(this.nextId)).findFirst();

        if (nextQuest.isEmpty()) {
            throw new IllegalStateException("[QuestPlugin] Trying to access to the unexisting \"" + this.nextId +
                    "\" (ID) next quest of the \"" + this.id + "\" (ID) quest.");
        }

        return nextQuest.get();
    }
}
