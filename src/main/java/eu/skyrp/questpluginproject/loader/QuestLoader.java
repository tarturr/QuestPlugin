package eu.skyrp.questpluginproject.loader;

import eu.skyrp.questpluginproject.quest.common.Quest;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public class QuestLoader {

    @Getter
    @Accessors(fluent = true)
    private final Map<String, YamlConfiguration> questConfigurations;

    public QuestLoader(File parentFolder) {
        this.questConfigurations = new HashMap<>();

        File[] children = parentFolder.listFiles();

        if (children != null) {
            for (File file : children) {
                if (file.isFile() && file.getName().endsWith(".yml") && !file.getName().startsWith("_") && !file.getName().equals("config.yml")) {
                    YamlConfiguration conf = YamlConfiguration.loadConfiguration(file);
                    String id = Objects.requireNonNull(conf.getString("quest.id"));
                    Optional<YamlConfiguration> found = this.getConfigurationByQuestId(id);

                    if (found.isPresent()) {
                        throw new IllegalStateException("[QuestPlugin] Two quests with the same \"" + id + "\" have " +
                                "been found in multiple quest configuration files. Please consider using a " +
                                "different quest ID for the \"" + file.getPath() + "\" or in the \"" +
                                found.get().getCurrentPath() + "\" quest configuration file.");
                    }

                    this.questConfigurations.put(id, conf);
                } else if (file.isDirectory()) {
                    this.questConfigurations.putAll(new QuestLoader(file).questConfigurations());
                }
            }
        }
    }

    public Optional<YamlConfiguration> getConfigurationByQuestId(String questId) {
        Optional<String> found = this.questConfigurations.keySet().stream().filter(questConf -> questConf.equals(questId)).findFirst();
        return found.map(this.questConfigurations::get);
    }

    public Optional<Quest> getQuestById(String id) {
        Optional<YamlConfiguration> possibleConf = this.getConfigurationByQuestId(id);

        if (possibleConf.isEmpty()) {
            return Optional.empty();
        }

        YamlConfiguration conf = possibleConf.get();
        return Optional.of(new Quest.Initializer().init(conf));
    }

}
