package eu.skyrp.questpluginproject.quest;

import eu.skyrp.questpluginproject.quest.common.Quest;
import eu.skyrp.questpluginproject.quest.common.QuestReward;
import eu.skyrp.questpluginproject.quest.common.types.QuestType;
import eu.skyrp.questpluginproject.quest.common.TransientQuest;
import eu.skyrp.questpluginproject.quest.manager.MechanicManager;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

@NoArgsConstructor
public class WeeklyQuest extends TransientQuest {

    @Builder
    public WeeklyQuest(String id, String name, List<String> lore, QuestReward reward, MechanicManager mechanicManager, int durationInDays) {
        super(QuestType.WEEKLY, id, name, lore, reward, mechanicManager, durationInDays);
    }

    @Override
    public void onQuestEnds(Player player) {
        player.sendMessage("§a[Quests] Quête terminée !");
    }

    @Override
    public void forceQuestEnd(Player player) {
        player.sendMessage("§c[Quests] Le temps requis pour terminer la quête s'est écoulé.");
    }

    public static WeeklyQuest createFromConfiguration(String name, YamlConfiguration conf) {
        WeeklyQuest quest = new WeeklyQuest();
        Quest.initQuestFromConfiguration(quest, conf);
        quest.nextId(conf.getString("quest.next"));

        return quest;
    }

}
