package eu.skyrp.questpluginproject.quest.common.types;

import eu.skyrp.questpluginproject.quest.common.QuestItem;
import eu.skyrp.questpluginproject.quest.common.mechanic.BaseMechanic;
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
import eu.skyrp.questpluginproject.quest.vanilla.mechanic.*;
import eu.skyrp.questpluginproject.quest.vanilla.objective.*;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

public enum MechanicType {
    BREAK((name, section) -> {
        BreakMechanic vanilla = new BreakMechanic(new ArrayList<>());
        IABreakMechanic itemsAdder = new IABreakMechanic(new ArrayList<>());
        SFBreakMechanic slimeFun = new SFBreakMechanic(new ArrayList<>());

        for (String itemSection : section.getKeys(false)) {
            QuestItem item = QuestItem.createFromConfigurationSection(name, section.getConfigurationSection(itemSection));

            switch (item.itemType()) {
                case VANILLA -> vanilla.objectives().add(new BreakQuestObjective(name, item.name(), item.amount()));
                case ITEMSADDER -> itemsAdder.objectives().add(new IABreakQuestObjective(name, item.name(), item.amount()));
                case SLIMEFUN -> slimeFun.objectives().add(new SFBreakQuestObjective(name, item.name(), item.amount()));
            }
        }

        return new BaseMechanic[] { vanilla, itemsAdder, slimeFun };
    }),
    COLLECT((name, section) -> {
        CollectMechanic vanilla = new CollectMechanic(new ArrayList<>());
        IACollectMechanic itemsAdder = new IACollectMechanic(new ArrayList<>());
        SFCollectMechanic slimeFun = new SFCollectMechanic(new ArrayList<>());

        for (String itemSection : section.getKeys(false)) {
            QuestItem item = QuestItem.createFromConfigurationSection(name, section.getConfigurationSection(itemSection));

            switch (item.itemType()) {
                case VANILLA -> vanilla.objectives().add(new CollectQuestObjective(name, item.name(), item.amount()));
                case ITEMSADDER -> itemsAdder.objectives().add(new IACollectQuestObjective(name, item.name(), item.amount()));
                case SLIMEFUN -> slimeFun.objectives().add(new SFCollectQuestObjective(name, item.name(), item.amount()));
            }
        }

        return new BaseMechanic[] { vanilla, itemsAdder, slimeFun };
    }),
    CONNECT((name, section) -> {
        ConnectMechanic mechanic = new ConnectMechanic(new ArrayList<>());

        int time = section.getInt("time");
        mechanic.objectives().add(new ConnectQuestObjective(name, time));

        return new ConnectMechanic[] { mechanic };
    }),
    KILL((name, section) -> {
        KillMechanic mechanic = new KillMechanic(new ArrayList<>());

        EntityType entityType = EntityType.valueOf(Objects.requireNonNull(section.getString("type")));
        int amount = section.getInt("amount");
        mechanic.objectives().add(new KillQuestObjective(name, entityType, amount));

        return new KillMechanic[] { mechanic };
    }),
    PLACE((name, section) -> {
        PlaceMechanic vanilla = new PlaceMechanic(new ArrayList<>());
        IAPlaceMechanic itemsAdder = new IAPlaceMechanic(new ArrayList<>());
        SFPlaceMechanic slimeFun = new SFPlaceMechanic(new ArrayList<>());

        for (String itemSection : section.getKeys(false)) {
            QuestItem item = QuestItem.createFromConfigurationSection(name, section.getConfigurationSection(itemSection));

            switch (item.itemType()) {
                case VANILLA -> vanilla.objectives().add(new PlaceQuestObjective(name, item.name(), item.amount()));
                case ITEMSADDER -> itemsAdder.objectives().add(new IAPlaceQuestObjective(name, item.name(), item.amount()));
                case SLIMEFUN -> slimeFun.objectives().add(new SFPlaceQuestObjective(name, item.name(), item.amount()));
            }
        }

        return new BaseMechanic[] { vanilla, itemsAdder, slimeFun };
    }),
    TRAVEL((name, section) -> {
        TravelMechanic mechanic = new TravelMechanic(new ArrayList<>());

        ConfigurationSection parentSection = Objects.requireNonNull(section.getParent());
        List<String> regionIds = Objects.requireNonNull(parentSection.getStringList(section.getName()));
        mechanic.objectives().add(new TravelQuestObjective(name, regionIds));

        return new BaseMechanic[] { mechanic };
    });

    @Getter
    @Accessors(fluent = true)
    private final BiFunction<String, ConfigurationSection, BaseMechanic<?>[]> associatedMechanics;

    MechanicType(BiFunction<String, ConfigurationSection, BaseMechanic<?>[]> associatedMechanics) {
        this.associatedMechanics = associatedMechanics;
    }
}
