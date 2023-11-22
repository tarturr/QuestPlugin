package eu.skyrp.questpluginproject.quest.mecanics.objective.all.other;

public class MalformedCustomItemIDException extends IllegalArgumentException {

    public MalformedCustomItemIDException(String apiName, String provided, String expected) {
        super("The \"" + provided + "\" item ID could not be found in the " + apiName + " register. Please consider " +
                "the use of the \"" + expected + ":item\" or \"" + apiName.toLowerCase() + ":item\" ID pattern.");
    }

}
