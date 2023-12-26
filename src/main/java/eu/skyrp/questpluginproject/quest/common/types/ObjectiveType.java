package eu.skyrp.questpluginproject.quest.common.types;

import eu.skyrp.questpluginproject.quest.common.initializer.Initializable;
import eu.skyrp.questpluginproject.quest.common.initializer.objective.BreakObjectiveInitializer;
import eu.skyrp.questpluginproject.quest.common.initializer.objective.CollectObjectiveInitializer;
import lombok.Getter;

@Getter
public enum ObjectiveType {
    BREAK(new BreakObjectiveInitializer()),
    COLLECT(new CollectObjectiveInitializer()),
    CONNECT(),
    KILL(),
    PLACE(),
    TRAVEL();

    private final Initializable<?> initializer;

    ObjectiveType(Initializable<?> initializer) {
        this.initializer = initializer;
    }
}
