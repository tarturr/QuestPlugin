package eu.skyrp.questpluginproject.lib.database;

import eu.skyrp.questpluginproject.lib.database.connection.BaseDatabaseConnection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Accessors(fluent = true)
@NoArgsConstructor
public abstract class DatabaseColumnAutoIncrement<T> implements DatabaseColumn<T, Integer> {

    @Setter
    private String table;

    @Getter
    private int columnId;

    protected DatabaseColumnAutoIncrement(String table) {
        this.table = table;
        this.columnId = -1;
    }

    protected abstract void createInDatabaseImpl(BaseDatabaseConnection connection);
    protected abstract void updateImpl(BaseDatabaseConnection connection);

    /**
     * Creates a new column instance in the database via the provided database using the {@link BaseDatabaseConnection}
     * class.
     *
     * @param connection The provided database connection.
     */
    @Override
    public final void createInDatabase(BaseDatabaseConnection connection) {
        this.createInDatabaseImpl(connection);

        try {
            PreparedStatement statement = connection.get().prepareStatement("SELECT MAX(id) FROM %s".formatted(table));
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                this.columnId = result.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public final void update(BaseDatabaseConnection connection) {
        if (this.existsInDatabase(connection)) {
            this.updateImpl(connection);
        } else {
            this.createInDatabase(connection);
        }
    }

    @Override
    public boolean existsInDatabase(BaseDatabaseConnection connection) {
        return this.columnId != -1;
    }

    public static String getIdsToString(List<? extends DatabaseColumnAutoIncrement<?>> list) {
        return BaseDatabaseConnection.createStringFromList(
                list.stream().map(DatabaseColumnAutoIncrement::columnId).toList(),
                Object::toString
        );
    }

}
