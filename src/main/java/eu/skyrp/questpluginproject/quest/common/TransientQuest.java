package eu.skyrp.questpluginproject.quest.common;

import eu.skyrp.questpluginproject.quest.manager.MechanicManager;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Accessors(fluent = true)
@NoArgsConstructor
public abstract class TransientQuest extends Quest {

    private Date end;

    public TransientQuest(QuestType type, String id, String name, List<String> lore, QuestReward reward, MechanicManager mechanicManager, Date end) {
        super(type, id, name, lore, reward, mechanicManager);
        this.end = end;
    }

    public TransientQuest(QuestType type, String id, String name, List<String> lore, QuestReward reward, MechanicManager mechanicManager, int durationInDays) {
        this(type, id, name, lore, reward, mechanicManager, new Date(System.currentTimeMillis() + (long) durationInDays * 24 * 3600 * 1000));
    }

    public void reload(Player player) {
        if (super.state() != QuestState.NOT_ACCESSIBLE && this.getTimeLeft() < 0 && super.state() == QuestState.PENDING) {
            super.state(QuestState.ENDED);
            this.forceQuestEnd(player);
        }
    }

    public abstract void forceQuestEnd(Player player);

    public long getTimeLeft() {
        return this.end.getTime() - System.currentTimeMillis();
    }

}
