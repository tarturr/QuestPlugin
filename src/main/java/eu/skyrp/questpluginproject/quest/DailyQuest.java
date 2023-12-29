package eu.skyrp.questpluginproject.quest;

import eu.skyrp.questpluginproject.quest.common.QuestReward;
import eu.skyrp.questpluginproject.quest.common.init.QuestInitializer;
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

    public static class Initializer extends QuestInitializer {}

    @Override
    public void onQuestEnds(Player player) {
        player.sendMessage("§a[Quests] Quête terminée !");
    }

    @Override
    public void forceQuestEnd(Player player) {
        player.sendMessage("§c[Quests] Le temps requis pour terminer la quête s'est écoulé.");
    }

}
