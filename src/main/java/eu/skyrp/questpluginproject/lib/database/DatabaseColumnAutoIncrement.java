package eu.skyrp.questpluginproject.lib.database;

import eu.skyrp.questpluginproject.lib.database.connection.BaseDatabaseConnection;

public interface DatabaseColumnAutoIncrement<T> extends DatabaseColumn<T> {
    public abstract int fetchId(BaseDatabaseConnection connection);

}
