package eu.skyrp.questpluginproject.lib.database;

import eu.skyrp.questpluginproject.lib.database.connection.BaseDatabaseConnection;

public interface DatabaseColumn<T> {

    boolean createInDatabase(BaseDatabaseConnection connection);
    boolean existsInDatabase(BaseDatabaseConnection connection);
    boolean update(BaseDatabaseConnection connection);
    boolean fetchFromDatabase(T primaryKey, BaseDatabaseConnection connection);

}
