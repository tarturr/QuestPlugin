package eu.skyrp.questpluginproject.lib.database.connection;

import eu.skyrp.questpluginproject.loaders.DatabaseConnectionLoader;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class DatabaseConnectionManager {

    private final Set<BaseDatabaseConnection> connections;

    public DatabaseConnectionManager(JavaPlugin plugin, String rootToAllDatabases) {
        this.connections = new HashSet<>();
        ConfigurationSection rootDatabaseSection = plugin.getConfig().getConfigurationSection(rootToAllDatabases);

        if (rootDatabaseSection == null) {
            throw new IllegalStateException("In config.yml: the root path \"" + rootToAllDatabases + "\" could not be" +
                    " found.");
        }

        for (String databaseName : rootDatabaseSection.getKeys(false)) {
            String path = rootToAllDatabases + "." + databaseName;

            DatabaseConnectionLoader connection = new DatabaseConnectionLoader(plugin, path, databaseName);
            connection.open();

            this.connections.add(connection.getConnection());
        }
    }

    public void closeAll() {
        this.connections.forEach(BaseDatabaseConnection::close);
    }

    public Optional<BaseDatabaseConnection> findDatabase(String name) {
        for (BaseDatabaseConnection connection : this.connections) {
            if (connection.getDatabaseName().equalsIgnoreCase(name)) {
                return Optional.of(connection);
            }
        }

        return Optional.empty();
    }

}
