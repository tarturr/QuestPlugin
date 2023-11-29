package eu.skyrp.questpluginproject.quest;

import eu.skyrp.questpluginproject.quest.common.Quest;
import eu.skyrp.questpluginproject.quest.common.QuestReward;
import eu.skyrp.questpluginproject.quest.common.QuestType;
import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import lombok.Builder;
import lombok.Singular;
import org.bukkit.entity.Player;

import java.util.List;

public class OnlyOnceQuest extends Quest {

    @Builder
    public OnlyOnceQuest(String id, String name, @Singular("descriptionLine") List<String> lore, @Singular("reward") List<QuestReward> rewards, @Singular("mechanic") List<BaseMechanic<?>> mechanics) {
        super(QuestType.ONLY_ONCE, id, name, lore, rewards, mechanics);
    }

    @Override
    public void onQuestEnds(Player player) {
        player.sendMessage("§a[Quests] Quête terminée !");
    }

}
