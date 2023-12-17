package eu.skyrp.questpluginproject.quest.common;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Accessors(fluent = true)
public class PlayerQuests implements PropertyChangeListener {
    private final UUID uuid;
    private final List<Quest> quests;

    private final PropertyChangeSupport support;

    public PlayerQuests(UUID uuid) {
        this.uuid = uuid;
        this.quests = new ArrayList<>();

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

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        Quest changedQuest = this.quests.stream().filter(quest -> quest.id().equals(event.getPropertyName())).findAny().orElseThrow();
        changedQuest.onQuestEnds(Bukkit.getPlayer(uuid));

        this.quests.remove(changedQuest);

        if (changedQuest.hasNext()) {
            this.support.firePropertyChange("nextQuest", this, changedQuest.nextId());
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    public void registerAllQuests(JavaPlugin main) {
        this.quests.forEach(quest -> quest.mechanicManager().registerAllMechanics(main));
    }
}
