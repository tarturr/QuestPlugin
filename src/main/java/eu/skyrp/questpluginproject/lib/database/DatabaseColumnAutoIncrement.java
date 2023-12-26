package eu.skyrp.questpluginproject.lib.database;

import eu.skyrp.questpluginproject.lib.database.connection.BaseDatabaseConnection;

/**
 * Represents a column of a database table which has a auto increment column as its primary key.
 * @see DatabaseColumn
 */
public interface DatabaseColumnAutoIncrement<T> extends DatabaseColumn<T, Integer> {

    /**
     * Fetch the id of the current class from the provided database using the {@link BaseDatabaseConnection} class.
     * @param connection The provided database connection.
     * @return the id if it was found in the database, a negative number (preferably -1) otherwise.
     */
    public abstract int fetchId(BaseDatabaseConnection connection);

}
