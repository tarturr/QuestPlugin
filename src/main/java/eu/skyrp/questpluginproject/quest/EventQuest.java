package eu.skyrp.questpluginproject.quest;

import eu.skyrp.questpluginproject.quest.common.Quest;
import eu.skyrp.questpluginproject.quest.common.QuestReward;
import eu.skyrp.questpluginproject.quest.common.types.QuestType;
import eu.skyrp.questpluginproject.quest.common.TransientQuest;
import eu.skyrp.questpluginproject.quest.manager.MechanicManager;
import lombok.*;
import lombok.experimental.Accessors;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Accessors(fluent = true)
@NoArgsConstructor
public class EventQuest extends TransientQuest {

    private Date start;
    private int replayable;

    @Builder
    public EventQuest(String id, String name, @Singular("descriptionLine") List<String> lore, QuestReward reward, MechanicManager mechanicManager, Date start, Date end, int replayable) {
        super(QuestType.EVENT, id, name, lore, reward, mechanicManager, end);
        this.start = start;
        this.replayable = replayable;
    }

    @Override
    public void reload(Player player) {
        if (this.getTimeBeforeStart() < 0) {
            super.reload(player);
        }
    }

    @Override
    public void onQuestEnds(Player player) {
        player.sendMessage("§a[Quests] Quête terminée !");
    }

    @Override
    public void forceQuestEnd(Player player) {
        player.sendMessage("§c[Quests] Le temps requis pour terminer la quête s'est écoulé.");
    }

    private long getTimeBeforeStart() {
        return this.start.getTime() - System.currentTimeMillis();
    }

    public static EventQuest createFromConfiguration(String name, YamlConfiguration conf) {
        EventQuest quest = new EventQuest();
        Quest.initQuestFromConfiguration(quest, conf);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            quest.start(format.parse(Objects.requireNonNull(conf.getString("quest.start"))));
            quest.end(format.parse(Objects.requireNonNull(conf.getString("quest.end"))));
        } catch (ParseException e) {
            throw new IllegalArgumentException("[QuestPlugin] The start or ending dates of the \"" + name + "\" are " +
                    "not formatted correctly. Please consider the use of the \"yyyy-MM-dd HH:mm\" date format.");
        }

        quest.replayable(conf.getInt("quest.replayable", 0));
        quest.nextId(conf.getString("quest.next"));
        return quest;
    }
}
