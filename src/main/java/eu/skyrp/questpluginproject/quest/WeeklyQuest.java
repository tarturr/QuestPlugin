package eu.skyrp.questpluginproject.quest;

import eu.skyrp.questpluginproject.quest.common.Quest;
import eu.skyrp.questpluginproject.quest.common.QuestReward;
import eu.skyrp.questpluginproject.quest.common.init.QuestInitializer;
import eu.skyrp.questpluginproject.quest.common.types.QuestType;
import eu.skyrp.questpluginproject.quest.common.TransientQuest;
import eu.skyrp.questpluginproject.quest.manager.MechanicManager;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;

@NoArgsConstructor
public class WeeklyQuest extends TransientQuest {

    @Builder
    public WeeklyQuest(String id, String name, List<String> lore, QuestReward reward, MechanicManager mechanicManager) {
        super(QuestType.WEEKLY, id, name, lore, reward, mechanicManager, 7, new WeeklyQuest.Initializer());
    }

    @Override
    public void onQuestEnds(Player player) {
        player.sendMessage("§a[Quests] Quête terminée !");
    }

    @Override
    public void forceQuestEnd(Player player) {
        player.sendMessage("§c[Quests] Le temps requis pour terminer la quête s'est écoulé.");
    }

    public static class Initializer extends QuestInitializer {
        @Override
        public Quest init(String id, ConfigurationSection section) {
            return super.init(id, section)
                    .nextId(section.getString("next"));
        }
    }

}
