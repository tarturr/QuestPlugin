package eu.skyrp.questpluginproject.quest;

import eu.skyrp.questpluginproject.quest.common.QuestReward;
import eu.skyrp.questpluginproject.quest.common.TransientQuest;
import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import lombok.Builder;
import org.bukkit.entity.Player;

import java.util.List;

public class WeeklyQuest extends TransientQuest {

    @Builder
    public WeeklyQuest(String id, String name, List<String> lore, QuestReward reward, List<BaseMechanic<?>> mechanics, int durationInDays) {
        super(id, name, lore, reward, mechanics, durationInDays);
    }

    @Override
    public void onQuestEnds(Player player) {
        player.sendMessage("§a[Quests] Quête terminée !");
    }

    @Override
    public void forceQuestEnd(Player player) {
        player.sendMessage("§c[Quests] Le temps requis pour terminer la quête s'est écoulé.");
    }

}
