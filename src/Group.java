package src;

import com.mysql.cj.xdevapi.SqlDataResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Group {

    private int id;
    private String name;

    public Group() {};

    public Group(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void saveToDB(Connection conn) throws SQLException {
        if (this.id == 0) {
            String query = "INSERT INTO Group(name) VALUES(?)";
            PreparedStatement preparedStatement
                    = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, this.name);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        } else {
            String query = "UPDATE Group SET name = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, this.name);
            preparedStatement.setInt(2, this.id);
            preparedStatement.executeUpdate();
        }
    }

    static public Group loadGroupById(Connection conn, int id) throws SQLException {
        String query = "SELECT * FROM Group WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            Group loadedGroup = new Group();
            loadedGroup.id = rs.getInt("id");
            loadedGroup.name = rs.getString("name");
            return loadedGroup;
        }
        return null;
    }

    static public Group[] loadAllGroups(Connection conn) throws SQLException {
        ArrayList<Group> groups = new ArrayList<>();
        String query = "SELECT * FROM Group";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Group loadedGroup = new Group();
            loadedGroup.id = rs.getInt("id");
            loadedGroup.name = rs.getString("name");
            groups.add(loadedGroup);
        }
        Group[] uGroup = new Group[groups.size()];
        uGroup = groups.toArray(uGroup);
        return uGroup;
    }

    public void delete(Connection conn) throws SQLException {
        if (this.id != 0) {
            String query = "DELETE FROM Group WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }

}
