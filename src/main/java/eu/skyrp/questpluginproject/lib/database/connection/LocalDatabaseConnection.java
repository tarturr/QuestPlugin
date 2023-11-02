package eu.skyrp.questpluginproject.lib.database.connection;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LocalDatabaseConnection extends BaseDatabaseConnection {

    public static final String SQLITE_PATH = "database/";

    private final String dbFilePath;

    public LocalDatabaseConnection(String dbName, String dbFilePath) {
        super("jdbc:sqlite:" + dbFilePath + dbName + ".db", dbName, DatabaseService.SQLITE);

        this.dbFilePath = dbFilePath;
    }

    @Override
    protected void connect() throws SQLException {
        File pathFolder = new File(this.dbFilePath);

        if (!pathFolder.exists()) {
            if (!pathFolder.mkdirs()) {
                System.err.println("An error occurred while creating parent folders for " + super.dbName + ".db file.");
            }
        }

        this.connection = DriverManager.getConnection(this.uri);
    }

}
