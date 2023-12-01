package eu.skyrp.questpluginproject.quest.common;

import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.List;

@Getter
@Accessors(fluent = true)
public abstract class TransientQuest extends Quest {

    private final Date end;

    public TransientQuest(QuestType type, String id, String name, List<String> lore, QuestReward reward, List<BaseMechanic<?>> mechanics, int durationInDays) {
        super(type, id, name, lore, reward, mechanics);
        this.end = new Date(System.currentTimeMillis() + (long) durationInDays * 24 * 3600 * 1000);
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
