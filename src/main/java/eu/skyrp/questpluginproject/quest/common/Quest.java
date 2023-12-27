package eu.skyrp.questpluginproject.quest.common;

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
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Accessors(fluent = true)
@NoArgsConstructor
public abstract class Quest implements PropertyChangeListener {

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

    private PropertyChangeSupport support;

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
        this.mechanicManager.addPropertyChangeListener(this);
        this.nextId = nextId;
        this.support = new PropertyChangeSupport(this);
    }

    public Quest(QuestType type, String id, String name, List<String> lore, QuestReward reward, MechanicManager mechanicManager, Quest next) {
        this(type, id, name, lore, reward, mechanicManager, next.id());
    }

    protected static void initQuestFromConfiguration(Quest quest, YamlConfiguration conf) {
        ConfigurationSection section = Objects.requireNonNull(conf.getConfigurationSection("quest"));

        quest.id(Objects.requireNonNull(section.getString("id")))
                .type(QuestType.valueOf(Objects.requireNonNull(section.getString("type")).toUpperCase()))
                .name(Objects.requireNonNull(section.getString("name")))
                .lore(section.getStringList("lore"))
                .reward(QuestReward.createFromConfigurationSection(conf.getCurrentPath(), section.getConfigurationSection("rewards")))
                .mechanicManager(MechanicManager.createFromConfigurationSection(conf.getCurrentPath(), Objects.requireNonNull(conf.getConfigurationSection("mechanics"))))
                .support(new PropertyChangeSupport(quest));
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
