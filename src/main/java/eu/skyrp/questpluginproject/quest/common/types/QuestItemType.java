package eu.skyrp.questpluginproject.quest.common.types;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public enum QuestItemType {

    VANILLA("", ""),
    ITEMSADDER("itemsadder", "ia"),
    SLIMEFUN("slimefun", "sf");

    private final String prefix;
    private final String shortPrefix;

    QuestItemType(String prefix, String shortPrefix) {
        this.prefix = prefix;
        this.shortPrefix = shortPrefix;
    }

    public static QuestItemType getTypeByName(String name) {
        if (!name.contains(":")) {
            return VANILLA;
        }

        String prefix = name.split(":")[0];

        for (QuestItemType type : values()) {
            if (prefix.equalsIgnoreCase(type.shortPrefix) || prefix.equalsIgnoreCase(type.prefix)) {
                return type;
            }
        }

        throw new IllegalArgumentException("[QuestPlugin] The provided \"" + prefix + "\" prefix does not " +
                "exist. Please consider the use of \"itemsadder\"/\"ia\" for ItemsAdder or \"slimefun\"/" +
                "\"sf\" for SlimeFun.");
    }

}
