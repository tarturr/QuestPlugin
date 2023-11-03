package eu.skyrp.questpluginproject.loaders;

import eu.skyrp.questpluginproject.lib.database.connection.BaseDatabaseConnection;
import eu.skyrp.questpluginproject.lib.database.connection.DatabaseConnection;
import eu.skyrp.questpluginproject.lib.database.connection.LocalDatabaseConnection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class DatabaseConnectionLoader {

    private final JavaPlugin plugin;
    private final FileConfiguration config;
    private final String rootDatabasePath;

    private final String service;
    private final String databaseName;

    private BaseDatabaseConnection databaseConnection;

    public DatabaseConnectionLoader(JavaPlugin plugin, String rootDatabasePath, String databaseName) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.rootDatabasePath = rootDatabasePath;

        this.service = config.getString(rootDatabasePath + ".service", BaseDatabaseConnection.DEFAULT_SERVICE);
        this.databaseName = databaseName;
    }

    public void open() {
        if (service.equalsIgnoreCase("sqlite")) {
            connectToLocalDatabase(this.databaseName);
        } else {
            connectToRemoteDatabase(this.databaseName, this.service);
        }
    }

    private void connectToLocalDatabase(String databaseName) {
        String filePath = this.config.getCurrentPath() + "plugins/" + this.plugin.getName() + "/" +
                this.config.getString(this.rootDatabasePath + ".sqlite_path", LocalDatabaseConnection.SQLITE_PATH);
        this.databaseConnection = new LocalDatabaseConnection(databaseName, filePath);

        if (this.databaseConnection.open()) {
            System.out.println("The connection to the " + databaseName + ".db database has successfully been established!");
        }
    }

    private void connectToRemoteDatabase(String databaseName, String service) {
        String host = this.config.getString(this.rootDatabasePath + ".host");
        String user = this.config.getString(this.rootDatabasePath + ".user");
        String pass = this.config.getString(this.rootDatabasePath + ".pass");
        int port = this.config.getInt(this.rootDatabasePath + ".port", -1);

        if (host == null || user == null || pass == null || port == -1) {
            System.err.println("The connection to the remote database could not be established " +
                    "because one or more of the information supplied is missing, of the wrong type or" +
                    "incorrect.\nTrying to establish a local SQLite database...");
            connectToLocalDatabase(databaseName);
        } else {
            this.databaseConnection = new DatabaseConnection.Builder()
                    .database(databaseName)
                    .service(service)
                    .host(host)
                    .username(user)
                    .password(pass)
                    .port(port)
                    .build();
        }

        if (this.databaseConnection.open()) {
            System.out.println("The connection to the remote " + databaseName + " database has successfully been established!");
        }
    }

    public BaseDatabaseConnection getConnection() {
        return databaseConnection;
    }
}
