package eu.skyrp.questpluginproject.quest.common;

import org.bukkit.configuration.file.YamlConfiguration;

public interface ConfigurationCreatable<T> {

    T createFromConfiguration(YamlConfiguration config);

}
