package eu.skyrp.questpluginproject.quest;

import eu.skyrp.questpluginproject.quest.common.QuestReward;
import eu.skyrp.questpluginproject.quest.common.QuestType;
import eu.skyrp.questpluginproject.quest.common.TransientQuest;
import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import lombok.Builder;
import lombok.Singular;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.List;

public class EventQuest extends TransientQuest {

    private final Date start;

    @Builder
    public EventQuest(String id, String name, @Singular("descriptionLine") List<String> lore, QuestReward reward, @Singular("mechanic") List<BaseMechanic<?>> mechanics, Date start, int durationInDays) {
        super(QuestType.EVENT, id, name, lore, reward, mechanics, durationInDays);
        this.start = start;
    }

    @Override
    public void reload(Player player) {
        if (this.getTimeBeforeStart() < 0) {
            super.reload(player);
        }
    }

    @Override
    public void onQuestEnds(Player player) {
        player.sendMessage("�a[Quests] Qu�te termin�e !");
    }

    @Override
    public void forceQuestEnd(Player player) {
        player.sendMessage("�c[Quests] Le temps requis pour terminer la qu�te s'est �coul�.");
    }

    private long getTimeBeforeStart() {
        return this.start.getTime() - System.currentTimeMillis();
    }

}
