package eu.skyrp.questpluginproject.quest;

import eu.skyrp.questpluginproject.quest.common.Quest;
import eu.skyrp.questpluginproject.quest.common.QuestReward;
import eu.skyrp.questpluginproject.quest.common.QuestType;
import eu.skyrp.questpluginproject.quest.manager.MechanicManager;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Singular;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

@NoArgsConstructor
public class OnlyOnceQuest extends Quest {

    @Builder
    public OnlyOnceQuest(String id, String name, @Singular("descriptionLine") List<String> lore, QuestReward reward, MechanicManager mechanicManager) {
        super(QuestType.ONLY_ONCE, id, name, lore, reward, mechanicManager);
    }

    @Override
    public void onQuestEnds(Player player) {
        player.sendMessage("§a[Quests] Quête terminée !");
    }

    public static OnlyOnceQuest createFromConfiguration(String name, YamlConfiguration conf) {
        OnlyOnceQuest quest = new OnlyOnceQuest();
        Quest.initQuestFromConfiguration(quest, conf);
        quest.nextId(conf.getString("quest.next"));

        return quest;
    }

}
