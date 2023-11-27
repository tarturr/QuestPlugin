package eu.skyrp.questpluginproject.quest;

import eu.skyrp.questpluginproject.quest.common.QuestReward;
import eu.skyrp.questpluginproject.quest.common.TransientQuest;
import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
import lombok.Singular;
import org.bukkit.entity.Player;

import java.util.List;

public class EventQuest extends TransientQuest {

    public EventQuest(String id, String name, @Singular("descriptionLine") List<String> lore, QuestReward reward, @Singular("mechanic") List<BaseMechanic<?>> mechanics, int durationInDays) {
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
