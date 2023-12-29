package eu.skyrp.questpluginproject.quest.common;

import eu.skyrp.questpluginproject.quest.common.init.QuestInitializer;
import eu.skyrp.questpluginproject.quest.common.types.QuestType;
import eu.skyrp.questpluginproject.quest.manager.MechanicManager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.entity.Player;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

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

    private QuestInitializer initializer;
    private PropertyChangeSupport support;

    public Quest(QuestType type, String id, String name, List<String> lore, QuestReward reward, MechanicManager mechanicManager, QuestInitializer initializer) {
        this(type, id, name, lore, reward, mechanicManager, (String) null, initializer);
    }

    public Quest(QuestType type, String id, String name, List<String> lore, QuestReward reward, MechanicManager mechanicManager, String nextId, QuestInitializer initializer) {
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

    public Quest(QuestType type, String id, String name, List<String> lore, QuestReward reward, MechanicManager mechanicManager, Quest next, QuestInitializer initializer) {
        this(type, id, name, lore, reward, mechanicManager, next.id(), initializer);
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
