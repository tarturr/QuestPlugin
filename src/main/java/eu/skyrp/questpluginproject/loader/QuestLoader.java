package eu.skyrp.questpluginproject.loader;

import eu.skyrp.questpluginproject.quest.DailyQuest;
import eu.skyrp.questpluginproject.quest.EventQuest;
import eu.skyrp.questpluginproject.quest.OnlyOnceQuest;
import eu.skyrp.questpluginproject.quest.WeeklyQuest;
import eu.skyrp.questpluginproject.quest.common.Quest;
import eu.skyrp.questpluginproject.quest.common.QuestType;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuestLoader {

    @Getter
    @Accessors(fluent = true)
    private final List<Quest> quests;

    public QuestLoader(File pluginFolder) {
        this.quests = new ArrayList<>();

        File[] children = pluginFolder.listFiles();

        if (children != null) {
            for (File file : children) {
                if (file.isFile() && file.getName().endsWith(".yml") && !file.getName().startsWith("_")) {
                    YamlConfiguration conf = YamlConfiguration.loadConfiguration(file);
                    String path = file.getPath();
                    Quest quest = switch (QuestType.valueOf(Objects.requireNonNull(conf.getString("quest.type")).toUpperCase())) {
                            case DAILY -> DailyQuest.createFromConfiguration(path, conf);
                            case EVENT -> EventQuest.createFromConfiguration(path, conf);
                            case ONLY_ONCE -> OnlyOnceQuest.createFromConfiguration(path, conf);
                            case WEEKLY -> WeeklyQuest.createFromConfiguration(path, conf);
                    };

                    if (this.quests.stream().anyMatch(loadedQuest -> loadedQuest.id().equals(quest.id()))) {
                        throw new IllegalStateException("[QuestPlugin] Two quests with the same \"" + quest.id() +
                                "\" have been found in multiple quest configuration files. Please consider using a " +
                                "different quest ID for the \"" + file.getPath() + "\" quest configuration file.");
                    }

                    this.quests.add(quest);
                } else if (file.isDirectory()) {
                    this.quests.addAll(new QuestLoader(file).quests());
                }
            }
        }
    }

}
