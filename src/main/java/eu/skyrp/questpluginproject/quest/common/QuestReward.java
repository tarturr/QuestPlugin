package eu.skyrp.questpluginproject.quest.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.Accessors;

import java.util.*;

@Getter
@Accessors(fluent = true)
public record QuestReward(@Singular("item") Map<String, Integer> items, float experience, float money, @Singular("command") List<String> commands) {
}
