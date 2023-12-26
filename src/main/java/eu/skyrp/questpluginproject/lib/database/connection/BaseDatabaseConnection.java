package eu.skyrp.questpluginproject.lib.database.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class BaseDatabaseConnection {

    public static final String DEFAULT_SERVICE = "sqlite";

    protected final String uri;
    protected final String dbName;
    protected final DatabaseService service;

    protected Connection connection;

    public BaseDatabaseConnection(String uri, String dbName, DatabaseService service) {
        this.uri = uri;
        this.dbName = dbName;
        this.service = service;
    }

    protected abstract void connect() throws SQLException;

    public boolean open() {
        try {
            connect();
            return true;
        } catch (SQLException e) {
            System.err.println("Une erreur est survenue lors de la tentative de connexion � la base de donn�es " +
                    "d'adresse " + this.uri + ".\nPlus d'informations: " + e);
            return false;
        }
    }

    public void close() {
        if (this.connection == null) {
            return;
        }

        try {
            if (!this.connection.isClosed()) {
                this.connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getDatabaseName() {
        return dbName;
    }

    public Connection get() {
        return connection;
    }

    public static <T> String createStringFromList(List<T> list, Function<T, String> turnToString) {
        return list.stream()
                .map(turnToString)
                .collect(Collectors.joining(","));
    }

    public static <T> List<T> fetchListFromString(String str, Function<String, T> turnStringToObject) {
        return Arrays.stream(str.split(","))
                .map(turnStringToObject)
                .toList();
    }

    public static List<Integer> fetchIntegerListFromString(String str) {
        return fetchListFromString(str, Integer::parseInt);
    }
}
