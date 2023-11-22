package eu.skyrp.questpluginproject.quest.mecanics.objective.all.other;

public class CustomItemNotFoundException extends IllegalArgumentException {

    public CustomItemNotFoundException(String apiName, String targetId) {
        super("The item with the \"" + targetId + "\" ID could not be found in the " + apiName + " registry.");
    }

}
