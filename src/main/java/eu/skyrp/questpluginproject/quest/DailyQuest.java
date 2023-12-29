package eu.skyrp.questpluginproject.quest;

import eu.skyrp.questpluginproject.quest.common.Quest;
import eu.skyrp.questpluginproject.quest.common.QuestReward;

import eu.skyrp.questpluginproject.quest.common.types.QuestType;
import eu.skyrp.questpluginproject.quest.common.TransientQuest;
import eu.skyrp.questpluginproject.quest.manager.MechanicManager;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Singular;
import org.bukkit.entity.Player;

import java.util.List;

@NoArgsConstructor
public class DailyQuest extends TransientQuest {

    @Builder
    public DailyQuest(String id, String name, @Singular("descriptionLine") List<String> lore, QuestReward reward, MechanicManager mechanicManager) {
        super(QuestType.DAILY, id, name, lore, reward, mechanicManager, 1, new DailyQuest.Initializer());
    }

    public static class Initializer extends Quest.Initializer {}

    @Override
    public void onQuestEnds(Player player) {
        player.sendMessage("�a[Quests] Qu�te termin�e !");
    }

    @Override
    public void forceQuestEnd(Player player) {
        player.sendMessage("�c[Quests] Le temps requis pour terminer la qu�te s'est �coul�.");
    }

}
