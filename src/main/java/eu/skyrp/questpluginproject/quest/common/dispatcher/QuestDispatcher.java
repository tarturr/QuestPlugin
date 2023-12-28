package eu.skyrp.questpluginproject.quest.common.dispatcher;

import eu.skyrp.questpluginproject.quest.DailyQuest;
import eu.skyrp.questpluginproject.quest.EventQuest;
import eu.skyrp.questpluginproject.quest.OnlyOnceQuest;
import eu.skyrp.questpluginproject.quest.WeeklyQuest;
import eu.skyrp.questpluginproject.quest.common.Quest;
import eu.skyrp.questpluginproject.quest.common.types.QuestType;

public class QuestDispatcher implements Dispatchable<Quest> {

    @Override
    public Quest dispatch(String str) {
        return switch (QuestType.valueOf(str)) {
            case EVENT -> new EventQuest();
            case DAILY -> new DailyQuest();
            case ONLY_ONCE -> new OnlyOnceQuest();
            case WEEKLY -> new WeeklyQuest();
        };
    }

}
