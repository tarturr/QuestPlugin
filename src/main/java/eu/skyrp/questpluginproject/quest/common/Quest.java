package eu.skyrp.questpluginproject.quest.common;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

@Getter
@Accessors(fluent = true)
public abstract class Quest implements PropertyChangeListener {

    private final String id;
    private QuestState state;

    private final QuestType type;
    private final String name;
    private final List<String> lore;
    private final List<QuestReward> rewards;
    private final Quest next;
    private final List<BaseMechanic<?>> mechanics;

    public Quest(QuestType type, String id, String name, List<String> lore, List<QuestReward> rewards, List<BaseMechanic<?>> mechanics) {
        this(type, id, name, lore, rewards, mechanics, null);
    }

    public Quest(QuestType type, String id, String name, List<String> lore, List<QuestReward> rewards, List<BaseMechanic<?>> mechanics, Quest next) {
        this.id = id;
        this.state = QuestState.NOT_STARTED;

        this.type = type;
        this.name = name;
        this.lore = lore;
        this.rewards = rewards;
        this.mechanics = mechanics;
        this.next = next;

        this.mechanics.forEach(mechanic -> mechanic.addPropertyChangeListener(this));
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("objectivesAllEnded")) {
            this.state = QuestState.ENDED;
            Player player = Bukkit.getPlayer(this.mechanics.stream().map(BaseMechanic::playerUUID).findAny().orElseThrow());

            if (player != null) {
                this.onQuestEnds(player);
                this.rewards.forEach(reward -> reward.giveToPlayer(player));
            }
        }
    }

    public abstract void onQuestEnds(Player player);

    public boolean hasNext() {
        return this.next != null;
    }

    public QuestType getType() {
        return type;
    }
}
