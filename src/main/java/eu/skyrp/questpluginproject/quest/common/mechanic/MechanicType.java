package eu.skyrp.questpluginproject.quest.common.mechanic;

import eu.skyrp.questpluginproject.quest.common.QuestItem;
import eu.skyrp.questpluginproject.quest.custom.itemsadder.mechanic.IABreakMechanic;
import eu.skyrp.questpluginproject.quest.custom.itemsadder.mechanic.IACollectMechanic;
import eu.skyrp.questpluginproject.quest.custom.itemsadder.mechanic.IAPlaceMechanic;
import eu.skyrp.questpluginproject.quest.custom.itemsadder.objective.IABreakQuestObjective;
import eu.skyrp.questpluginproject.quest.custom.itemsadder.objective.IACollectQuestObjective;
import eu.skyrp.questpluginproject.quest.custom.itemsadder.objective.IAPlaceQuestObjective;
import eu.skyrp.questpluginproject.quest.custom.slimefun.mechanic.SFBreakMechanic;
import eu.skyrp.questpluginproject.quest.custom.slimefun.mechanic.SFCollectMechanic;
import eu.skyrp.questpluginproject.quest.custom.slimefun.mechanic.SFPlaceMechanic;
import eu.skyrp.questpluginproject.quest.custom.slimefun.objective.SFBreakQuestObjective;
import eu.skyrp.questpluginproject.quest.custom.slimefun.objective.SFCollectQuestObjective;
import eu.skyrp.questpluginproject.quest.custom.slimefun.objective.SFPlaceQuestObjective;
import eu.skyrp.questpluginproject.quest.vanilla.mechanic.BreakMechanic;
import eu.skyrp.questpluginproject.quest.vanilla.mechanic.CollectMechanic;
import eu.skyrp.questpluginproject.quest.vanilla.mechanic.ConnectMechanic;
import eu.skyrp.questpluginproject.quest.vanilla.mechanic.PlaceMechanic;
import eu.skyrp.questpluginproject.quest.vanilla.objective.BreakQuestObjective;
import eu.skyrp.questpluginproject.quest.vanilla.objective.CollectQuestObjective;
import eu.skyrp.questpluginproject.quest.vanilla.objective.ConnectQuestObjective;
import eu.skyrp.questpluginproject.quest.vanilla.objective.PlaceQuestObjective;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.function.BiFunction;

public enum MechanicType {
    BREAK((name, section) -> {
        BaseMechanic<?>[] possibleMechanics = new BaseMechanic[]{
                new BreakMechanic(new ArrayList<>()),
                new IABreakMechanic(new ArrayList<>()),
                new SFBreakMechanic(new ArrayList<>())
        };

        for (String itemSection : section.getKeys(false)) {
            QuestItem item = QuestItem.createFromConfigurationSection(name, section.getConfigurationSection(itemSection));

            switch (item.itemType()) {
                case VANILLA -> ((BreakMechanic) possibleMechanics[0]).objectives().add(new BreakQuestObjective(name, item.name(), item.amount()));
                case ITEMSADDER -> ((IABreakMechanic) possibleMechanics[1]).objectives().add(new IABreakQuestObjective(name, item.name(), item.amount()));
                case SLIMEFUN -> ((SFBreakMechanic) possibleMechanics[2]).objectives().add(new SFBreakQuestObjective(name, item.name(), item.amount()));
            }
        }

        return possibleMechanics;
    }),
    COLLECT((name, section) -> {
        BaseMechanic<?>[] possibleMechanics = new BaseMechanic[] {
                new CollectMechanic(new ArrayList<>()),
                new IACollectMechanic(new ArrayList<>()),
                new SFCollectMechanic(new ArrayList<>())
        };

        for (String itemSection : section.getKeys(false)) {
            QuestItem item = QuestItem.createFromConfigurationSection(name, section.getConfigurationSection(itemSection));

            switch (item.itemType()) {
                case VANILLA -> ((CollectMechanic) possibleMechanics[0]).objectives().add(new CollectQuestObjective(name, item.name(), item.amount()));
                case ITEMSADDER -> ((IACollectMechanic) possibleMechanics[1]).objectives().add(new IACollectQuestObjective(name, item.name(), item.amount()));
                case SLIMEFUN -> ((SFCollectMechanic) possibleMechanics[2]).objectives().add(new SFCollectQuestObjective(name, item.name(), item.amount()));
            }
        }

        return possibleMechanics;
    }),
    CONNECT((name, section) -> {
        BaseMechanic<?>[] possibleMechanics = new BaseMechanic[] {
                new ConnectMechanic(new ArrayList<>())
        };

        int time = section.getInt("time");
        ((ConnectMechanic) possibleMechanics[0]).objectives().add(new ConnectQuestObjective(name, time));

        return possibleMechanics;
    }),
    KILL((name, section) -> { throw new NotImplementedException("[QuestPlugin] The kill mechanic is not implemented yet."); }), // TODO: Implement this feature
    PLACE((name, section) -> {
        BaseMechanic<?>[] possibleMechanics = new BaseMechanic[] {
                new PlaceMechanic(new ArrayList<>()),
                new IAPlaceMechanic(new ArrayList<>()),
                new IACollectMechanic(new ArrayList<>())
        };

        for (String itemSection : section.getKeys(false)) {
            QuestItem item = QuestItem.createFromConfigurationSection(name, section.getConfigurationSection(itemSection));

            switch (item.itemType()) {
                case VANILLA -> ((PlaceMechanic) possibleMechanics[0]).objectives().add(new PlaceQuestObjective(name, item.name(), item.amount()));
                case ITEMSADDER -> ((IAPlaceMechanic) possibleMechanics[1]).objectives().add(new IAPlaceQuestObjective(name, item.name(), item.amount()));
                case SLIMEFUN -> ((SFPlaceMechanic) possibleMechanics[2]).objectives().add(new SFPlaceQuestObjective(name, item.name(), item.amount()));
            }
        }

        return possibleMechanics;
    }),
    TRAVEL((name, section) -> { throw new NotImplementedException("[QuestPlugin] The travel mechanic is not implemented yet."); }); // TODO: Implement this feature

    @Getter
    @Accessors(fluent = true)
    private final BiFunction<String, ConfigurationSection, BaseMechanic<?>[]> associatedMechanics;

    MechanicType(BiFunction<String, ConfigurationSection, BaseMechanic<?>[]> associatedMechanics) {
        this.associatedMechanics = associatedMechanics;
    }
}
