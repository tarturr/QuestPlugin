package eu.skyrp.questpluginproject.quest;

import eu.skyrp.questpluginproject.quest.common.QuestReward;
import eu.skyrp.questpluginproject.quest.common.QuestType;
import eu.skyrp.questpluginproject.quest.common.TransientQuest;
import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import lombok.Builder;
import lombok.Singular;
import org.bukkit.entity.Player;

import java.util.List;

public class EventQuest extends TransientQuest {

    @Builder
    public EventQuest(String id, String name, @Singular("descriptionLine") List<String> lore, @Singular("reward") List<QuestReward> rewards, @Singular("mechanic") List<BaseMechanic<?>> mechanics, int durationInDays) {
        super(QuestType.EVENT, id, name, lore, rewards, mechanics, durationInDays);
    }

    @Override
    public void onQuestEnds(Player player) {
        player.sendMessage("�a[Quests] Qu�te termin�e !");
    }

    @Override
    public void forceQuestEnd(Player player) {
        player.sendMessage("�c[Quests] Le temps requis pour terminer la qu�te s'est �coul�.");
    }

}
