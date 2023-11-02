package eu.skyrp.questpluginproject.quest;

import eu.skyrp.questpluginproject.lib.database.connection.BaseDatabaseConnection;
import eu.skyrp.questpluginproject.lib.database.DatabaseColumn;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.Accessors;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Accessors(fluent = true)
@Builder
public class Quest implements DatabaseColumn<String> {

    private String id;
    private QuestType type;
    private String name;
    @Singular("descriptionLine")
    private List<String> lore;
    private QuestReward reward;

    public Quest() {
        this(null, null, null, null, null);
    }

    public Quest(String id, QuestType type, String name, List<String> lore, QuestReward reward) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.lore = lore;
        this.reward = reward;
    }

    @Override
    public boolean createInDatabase(BaseDatabaseConnection connection) {
        if (!this.reward.createInDatabase(connection)) {
            return false;
        }

        try {
            PreparedStatement insert = connection.get().prepareStatement(
                    "INSERT INTO quest(id, type, name, lore, reward_id)\n" +
                            "VALUES(?, ?, ?, ?, ?)"
            );

            insert.setString(1, this.id);
            insert.setString(2, this.type.toString());
            insert.setString(3, this.name);
            insert.setString(4, this.loreToString(this.lore));
            insert.setInt(5, this.reward.id());

            insert.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("An error has occurred while creating a new quest into the database. " +
                    "More information: " + e);

            return false;
        }
    }

    @Override
    public boolean existsInDatabase(BaseDatabaseConnection connection) {
        try {
            PreparedStatement query = connection.get().prepareStatement(
                    "SELECT COUNT(*) FROM quest\n" +
                            "WHERE id = ?"
            );

            query.setString(1, this.id);
            return query.executeQuery().next();
        } catch (SQLException e) {
            System.err.println("An error has occurred while checking a quest into the database. " +
                    "More information: " + e);
            return false;
        }
    }

    @Override
    public boolean update(BaseDatabaseConnection connection) {
        try {
            PreparedStatement insert = connection.get().prepareStatement(
                    "UPDATE quest\n" +
                            "SET type = ?,\n" +
                            "name = ?,\n" +
                            "lore = ?,\n" +
                            "reward_id = ?\n" +
                            "WHERE id = ?"
            );

            insert.setString(1, this.type.toString());
            insert.setString(2, this.name);
            insert.setString(3, this.loreToString(this.lore));
            insert.setInt(4, this.reward.id());
            insert.setString(5, this.id);

            insert.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("An error has occurred while updating a quest into the database. " +
                    "More information: " + e);

            return false;
        }
    }

    @Override
    public boolean fetchFromDatabase(String primaryKey, BaseDatabaseConnection connection) {
        try {
            PreparedStatement query = connection.get().prepareStatement(
                    "SELECT * FROM quest\n" +
                            "WHERE id = ?"
            );

            query.setString(1, primaryKey);
            ResultSet result = query.executeQuery();

            if (result.next()) {
                this.id = result.getString(1);
                this.type = QuestType.valueOf(result.getString(2));
                this.name = result.getString(3);
                this.lore = this.extractLore(result.getString(4));
                this.reward = new QuestReward();
                this.reward.fetchFromDatabase(result.getInt(5), connection);

                return true;
            }
        } catch (SQLException e) {
            System.err.println("An error has occurred while fetching a quest reward from the database. " +
                    "More information: " + e);
        }

        return false;
    }

    private String loreToString(List<String> lore) {
        StringBuilder result = new StringBuilder();

        for (String line : lore) {
            result.append(line).append(',');
        }

        return result.toString();
    }

    private List<String> extractLore(String string) {
        return new ArrayList<>(Arrays.asList(string.split(",")));
    }
}
