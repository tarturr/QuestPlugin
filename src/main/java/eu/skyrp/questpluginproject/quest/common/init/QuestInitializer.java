package eu.skyrp.questpluginproject.quest.common.init;

import eu.skyrp.questpluginproject.lib.database.connection.BaseDatabaseConnection;
import eu.skyrp.questpluginproject.quest.common.Quest;
import eu.skyrp.questpluginproject.quest.common.QuestReward;
import eu.skyrp.questpluginproject.quest.common.QuestState;
import eu.skyrp.questpluginproject.quest.common.dispatcher.QuestDispatcher;
import eu.skyrp.questpluginproject.quest.manager.MechanicManager;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class QuestInitializer implements Initializable<Quest> {

    private final QuestDispatcher dispatcher;

    @Setter
    @Accessors(fluent = true)
    private Quest quest;

    public QuestInitializer() {
        this.dispatcher = new QuestDispatcher();
    }

    @Override
    public Quest init(String id, ConfigurationSection section) {
        return (this.quest == null ? this.dispatcher.dispatch(Objects.requireNonNull(section.getString("type"))) : this.quest)
                .id(Objects.requireNonNull(section.getString("id")))
                .name(Objects.requireNonNull(section.getString("name")))
                .lore(section.getStringList("lore"))
                .reward(new QuestReward.Initializer().init(id, section.getConfigurationSection("rewards")))
                .mechanicManager(new MechanicManager.Initializer().init(id, Objects.requireNonNull(Objects.requireNonNull(section.getParent()).getConfigurationSection("mechanics"))));
    }

    @Override
    public Quest init(int id, BaseDatabaseConnection connection) {
        try {
            PreparedStatement statement = connection.get().prepareStatement("""
                        SELECT * FROM quest
                        WHERE id = ?
                        """.stripIndent()
            );

            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Quest quest = this.dispatcher.dispatch(result.getString(4));

                quest.id(result.getString(2))
                        .state(QuestState.valueOf(result.getString(3)))
                        .mechanicManager(new MechanicManager.Initializer().init(result.getInt(5), connection));
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("[QuestPlugin] An error has occurred while fetching the quest with " +
                    "the " + id + " ID: " + e);
        }

        throw new IllegalArgumentException("[QuestPlugin] The quest with the " + id + " ID does not exist in " +
                "\"quest\" database table.");
    }

    public Quest init(YamlConfiguration conf) {
        ConfigurationSection section = Objects.requireNonNull(conf.getConfigurationSection("quest"));
        String id = Objects.requireNonNull(section.getString("id"));
        Quest quest = this.dispatcher.dispatch(Objects.requireNonNull(section.getString("type")));

        return quest.initializer().quest(quest).init(id, section);
    }

}
