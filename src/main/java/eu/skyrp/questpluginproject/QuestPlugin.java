package eu.skyrp.questpluginproject;

import eu.skyrp.questpluginproject.lib.database.connection.DatabaseConnectionManager;
import eu.skyrp.questpluginproject.quest.Quest;
import eu.skyrp.questpluginproject.quest.QuestReward;
import eu.skyrp.questpluginproject.quest.QuestType;
import org.bukkit.plugin.java.JavaPlugin;

public final class QuestPlugin extends JavaPlugin {

    private DatabaseConnectionManager connections;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.connections = new DatabaseConnectionManager(this, "databases");

        Quest test = Quest.builder()
                .id("test")
                .type(QuestType.ONLY_ONCE)
                .name("Quête de test")
                .descriptionLine("Ceci est une")
                .descriptionLine("quête de test.")
                .reward(QuestReward.builder()
                        .item("testitem", 1)
                        .experience(250.0f)
                        .money(10.0f)
                        .command("/hello")
                        .build())
                .build();

        this.connections.findDatabase("quests").ifPresent(test::createInDatabase);
    }

    @Override
    public void onDisable() {
        this.connections.closeAll();
    }

}
