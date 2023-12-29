package eu.skyrp.questpluginproject.quest;

import eu.skyrp.questpluginproject.quest.common.Quest;
import eu.skyrp.questpluginproject.quest.common.QuestReward;

import eu.skyrp.questpluginproject.quest.common.types.QuestType;
import eu.skyrp.questpluginproject.quest.manager.MechanicManager;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Singular;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;

@NoArgsConstructor
public class OnlyOnceQuest extends Quest {

    @Builder
    public OnlyOnceQuest(String id, String name, @Singular("descriptionLine") List<String> lore, QuestReward reward, MechanicManager mechanicManager) {
        super(QuestType.ONLY_ONCE, id, name, lore, reward, mechanicManager, new OnlyOnceQuest.Initializer());
    }

    @Override
    public void onQuestEnds(Player player) {
        player.sendMessage("§a[Quests] Quête terminée !");
    }

    public static class Initializer extends Quest.Initializer {
        @Override
        public Quest init(String id, ConfigurationSection section) {
            return super.init(id, section)
                    .nextId(section.getString("next"));
        }
    }

}
