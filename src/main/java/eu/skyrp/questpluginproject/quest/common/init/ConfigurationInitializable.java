package eu.skyrp.questpluginproject.quest.common.init;

import org.bukkit.configuration.ConfigurationSection;

public interface ConfigurationInitializable<T> {

    T init(String id, ConfigurationSection section);

}