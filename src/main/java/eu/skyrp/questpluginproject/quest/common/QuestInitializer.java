package eu.skyrp.questpluginproject.quest.common;

import eu.skyrp.questpluginproject.quest.DailyQuest;
import eu.skyrp.questpluginproject.quest.EventQuest;
import eu.skyrp.questpluginproject.quest.OnlyOnceQuest;
import eu.skyrp.questpluginproject.quest.WeeklyQuest;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Objects;

public class QuestInitializer {

    public static Quest init(YamlConfiguration conf) {
        String path = conf.getCurrentPath();

        return switch (QuestType.valueOf(Objects.requireNonNull(conf.getString("quest.type")).toUpperCase())) {
            case DAILY -> DailyQuest.createFromConfiguration(path, conf);
            case EVENT -> EventQuest.createFromConfiguration(path, conf);
            case ONLY_ONCE -> OnlyOnceQuest.createFromConfiguration(path, conf);
            case WEEKLY -> WeeklyQuest.createFromConfiguration(path, conf);
        };
    }

}
