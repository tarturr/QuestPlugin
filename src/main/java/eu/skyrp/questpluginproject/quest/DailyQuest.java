package eu.skyrp.questpluginproject.quest;

import eu.skyrp.questpluginproject.quest.common.QuestReward;
import eu.skyrp.questpluginproject.quest.common.QuestType;
import eu.skyrp.questpluginproject.quest.common.TransientQuest;
import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import lombok.Builder;
import lombok.Singular;
import org.bukkit.entity.Player;

import java.util.List;

public class DailyQuest extends TransientQuest {

    @Builder
    public DailyQuest(String id, String name, @Singular("descriptionLine") List<String> lore, @Singular("reward") List<QuestReward> rewards, @Singular("mechanic") List<BaseMechanic<?>> mechanics) {
        super(QuestType.DAILY, id, name, lore, rewards, mechanics, 1);
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
