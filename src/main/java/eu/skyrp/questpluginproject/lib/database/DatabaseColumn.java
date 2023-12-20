package eu.skyrp.questpluginproject.lib.database;

import eu.skyrp.questpluginproject.lib.database.connection.BaseDatabaseConnection;

/**
 * Represents a column of a database table.
 * @param <T> The type of the primary key.
 */
public interface DatabaseColumn<T> {

    /**
     * Creates a new column instance in the database via the provided database using the {@link BaseDatabaseConnection}
     * class.
     * @param connection The provided database connection.
     * @return true if the creation was successful, false otherwise.
     */
    boolean createInDatabase(BaseDatabaseConnection connection);

    /**
     * Checks if the current class exists in the provided database using the {@link BaseDatabaseConnection} class.
     * @param connection The provided database connection.
     * @return true if the check was successful, false otherwise.
     */
    boolean existsInDatabase(BaseDatabaseConnection connection);

    /**
     * Updates the new values of the class fields in the provided database using the {@link BaseDatabaseConnection}
     * class.
     * @param connection The provided database connection.
     * @return true if the update was successful, false otherwise.
     */
    boolean update(BaseDatabaseConnection connection);

    /**
     * Fetches an instance of the T class from the provided database using the {@link BaseDatabaseConnection} class.
     * @param primaryKey The value of the primary key.
     * @param connection The provided database connection.
     * @return true if the instance was successfully created, false otherwise.
     */
    boolean fetchFromDatabase(T primaryKey, BaseDatabaseConnection connection);

}
