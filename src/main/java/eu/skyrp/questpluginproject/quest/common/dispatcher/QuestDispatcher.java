package eu.skyrp.questpluginproject.quest.common.dispatcher;

import eu.skyrp.questpluginproject.quest.DailyQuest;
import eu.skyrp.questpluginproject.quest.EventQuest;
import eu.skyrp.questpluginproject.quest.OnlyOnceQuest;
import eu.skyrp.questpluginproject.quest.WeeklyQuest;
import eu.skyrp.questpluginproject.quest.common.Quest;
import eu.skyrp.questpluginproject.quest.common.types.QuestType;

import java.beans.PropertyChangeSupport;

public class QuestDispatcher implements Dispatchable<Quest> {

    @Override
    public Quest dispatch(String str) {
        Quest quest = switch (QuestType.valueOf(str.toUpperCase())) {
            case EVENT -> new EventQuest().initializer(new EventQuest.Initializer());
            case DAILY -> new DailyQuest().initializer(new DailyQuest.Initializer());
            case ONLY_ONCE -> new OnlyOnceQuest().initializer(new OnlyOnceQuest.Initializer());
            case WEEKLY -> new WeeklyQuest().initializer(new WeeklyQuest.Initializer());
        };

        quest.table("quest");
        quest.support(new PropertyChangeSupport(quest));
        return quest;
    }

}
