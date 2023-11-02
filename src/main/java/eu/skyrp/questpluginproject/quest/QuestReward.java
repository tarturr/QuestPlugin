package eu.skyrp.questpluginproject.quest;

import eu.skyrp.questpluginproject.lib.database.DatabaseColumnAutoIncrement;
import eu.skyrp.questpluginproject.lib.database.connection.BaseDatabaseConnection;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.Accessors;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Getter
@Accessors(fluent = true)
public class QuestReward implements DatabaseColumnAutoIncrement<Integer> {

    private int id;
    private Map<String, Integer> items;
    private float experience;
    private float money;
    private List<String> commands;

    public QuestReward() {
        this.id = -1;
        this.items = null;
        this.experience = 0.0f;
        this.money = 0.0f;
        this.commands = null;
    }

    @Builder
    public QuestReward(@Singular("item") Map<String, Integer> items, float experience, float money, @Singular("command") List<String> commands) {
        this.id = -1;
        this.items = items;
        this.experience = experience;
        this.money = money;
        this.commands = commands;
    }

    @Override
    public boolean createInDatabase(BaseDatabaseConnection connection) {
        try {
            PreparedStatement insert = connection.get().prepareStatement(
                    "INSERT INTO questreward(items, experience, money, commands)\n" +
                            "VALUES(?, ?, ?, ?)"
            );

            insert.setString(1, this.itemsDataToString(this.items));
            insert.setFloat(2, this.experience);
            insert.setFloat(3, this.money);
            insert.setString(4, this.commandsToString(this.commands));

            insert.executeUpdate();

            this.id = this.fetchId(connection);
            return true;
        } catch (SQLException e) {
            System.err.println("An error has occurred while creating a new quest reward into the database. " +
                    "More information: " + e);

            return false;
        }
    }

    @Override
    public boolean existsInDatabase(BaseDatabaseConnection connection) {
        if (this.id == -1) {
            throw new IllegalStateException("Quest reward ID cannot be negative.");
        }

        try {
            PreparedStatement query = connection.get().prepareStatement(
                    "SELECT COUNT(*) FROM questreward\n" +
                            "WHERE id = ?"
            );

            query.setInt(1, this.id);
            return query.executeQuery().next();
        } catch (SQLException e) {
            System.err.println("An error has occurred while checking a quest reward into the database. " +
                    "More information: " + e);
            return false;
        }
    }

    @Override
    public boolean update(BaseDatabaseConnection connection) {
        if (this.id == -1) {
            throw new IllegalStateException("Quest reward ID cannot be negative.");
        }

        try {
            PreparedStatement insert = connection.get().prepareStatement(
                    "UPDATE questreward\n" +
                            "SET items = ?,\n" +
                                "experience = ?,\n" +
                                "money = ?,\n" +
                                "commands = ?\n" +
                            "WHERE id = ?"
            );

            insert.setString(1, this.itemsDataToString(this.items));
            insert.setFloat(2, this.experience);
            insert.setFloat(3, this.money);
            insert.setString(4, this.commandsToString(this.commands));
            insert.setInt(5, this.id);

            insert.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("An error has occurred while updating a quest reward into the database. " +
                    "More information: " + e);

            return false;
        }
    }

    @Override
    public boolean fetchFromDatabase(Integer primaryKey, BaseDatabaseConnection connection) {
        try {
            PreparedStatement query = connection.get().prepareStatement(
                    "SELECT * FROM questreward\n" +
                            "WHERE id = ?"
            );

            query.setInt(1, primaryKey);
            ResultSet result = query.executeQuery();

            if (result.next()) {
                this.id = result.getInt(1);
                this.items = this.extractItemsData(result.getString(2));
                this.experience = result.getFloat(3);
                this.money = result.getFloat(4);
                this.commands = this.extractCommands(result.getString(5));

                return true;
            }
        } catch (SQLException e) {
            System.err.println("An error has occurred while fetching a quest reward from the database. " +
                    "More information: " + e);
        }

        return false;
    }

    @Override
    public int fetchId(BaseDatabaseConnection connection) {
        try {
            ResultSet result = connection.get().prepareStatement(
                    "SELECT COALESCE(MAX(id), -1) FROM questreward"
            ).executeQuery();

            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("An error has occurred while looking for a quest reward ID into the database. " +
                    "More information: " + e);
        }

        return 0;
    }

    private String itemsDataToString(Map<String, Integer> items) {
        StringBuilder result = new StringBuilder();

        for (String key : items.keySet()) {
            result.append(key).append('=').append(items.get(key)).append(';');
        }

        return result.toString();
    }

    private Map<String, Integer> extractItemsData(String str) {
        Map<String, Integer> items = new HashMap<>();

        for (String element : str.split(";")) {
            String[] pair = element.split("=");
            items.put(pair[0], Integer.parseInt(pair[1]));
        }

        return items;
    }

    private String commandsToString(List<String> commands) {
        StringBuilder result = new StringBuilder();

        for (String command : commands) {
            result.append(command).append(',');
        }

        return result.toString();
    }

    private List<String> extractCommands(String string) {
        return new ArrayList<>(Arrays.asList(string.split(",")));
    }
}
