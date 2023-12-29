package eu.skyrp.questpluginproject.quest.common.init;

import eu.skyrp.questpluginproject.lib.database.connection.BaseDatabaseConnection;

public interface DatabaseInitializable<T> {

    T init(int id, BaseDatabaseConnection connection);

}
