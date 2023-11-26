package eu.skyrp.questpluginproject.quest.custom.common;

import java.util.function.Function;
import java.util.function.Predicate;

public class CustomObjectFinder {

    public static <T> T getById(String targetId, String apiName, String shortPrefix, Predicate<String> isInRegistry, Function<String, T> getItem) {
        if (!targetId.contains(":")) {
            throw new MalformedCustomItemIDException(apiName, targetId, shortPrefix);
        }

        String[] parts  = targetId.split(":");
        String prefix   = parts[0];
        String item     = parts[1];

        if (!(prefix.equalsIgnoreCase(shortPrefix) || prefix.equalsIgnoreCase(apiName.toLowerCase()))) {
            throw new MalformedCustomItemIDException(apiName, targetId, shortPrefix);
        }

        if (!isInRegistry.test(item)) {
            throw new CustomItemNotFoundException(apiName, targetId);
        }

        return getItem.apply(item);
    }

}
