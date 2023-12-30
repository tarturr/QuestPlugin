package eu.skyrp.questpluginproject.quest.common.dispatcher;

import eu.skyrp.questpluginproject.quest.common.objective.BaseQuestObjective;
import eu.skyrp.questpluginproject.quest.common.types.MechanicType;
import eu.skyrp.questpluginproject.quest.custom.itemsadder.objective.IABreakQuestObjective;
import eu.skyrp.questpluginproject.quest.custom.itemsadder.objective.IACollectQuestObjective;
import eu.skyrp.questpluginproject.quest.custom.itemsadder.objective.IAPlaceQuestObjective;
import eu.skyrp.questpluginproject.quest.custom.slimefun.objective.SFBreakQuestObjective;
import eu.skyrp.questpluginproject.quest.custom.slimefun.objective.SFCollectQuestObjective;
import eu.skyrp.questpluginproject.quest.custom.slimefun.objective.SFPlaceQuestObjective;
import eu.skyrp.questpluginproject.quest.vanilla.objective.*;

public class QuestObjectiveDispatcher implements Dispatchable<BaseQuestObjective<?, ?>> {

    @Override
    public BaseQuestObjective<?, ?> dispatch(String str) {
        return switch (MechanicType.valueOf(str.toUpperCase())) {
            case BREAK -> new BreakQuestObjective();
            case IA_BREAK -> new IABreakQuestObjective();
            case SF_BREAK -> new SFBreakQuestObjective();
            case COLLECT -> new CollectQuestObjective();
            case IA_COLLECT -> new IACollectQuestObjective();
            case SF_COLLECT -> new SFCollectQuestObjective();
            case CONNECT -> new ConnectQuestObjective();
            case KILL -> new KillQuestObjective();
            case PLACE -> new PlaceQuestObjective();
            case IA_PLACE -> new IAPlaceQuestObjective();
            case SF_PLACE -> new SFPlaceQuestObjective();
            case TRAVEL -> new TravelQuestObjective();
        };
    }
}
