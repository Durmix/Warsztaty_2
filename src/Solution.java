package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Solution {

    private int id;
    private String created;
    private String updated;
    private String description;

    public Solution() {};

    public Solution(String created, String updated, String description) {
        this.created = created;
        this.updated = updated;
        this.description = description;
    }

    public void saveToDB(Connection conn) throws SQLException {
        if (this.id == 0) {
            String query = "INSERT INTO Solution(created, updated, description) VALUES(?, ?, ?)";
            PreparedStatement preparedStatement
                    = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, this.created);
            preparedStatement.setString(2, this.updated);
            preparedStatement.setString(3, this.description);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        } else {
            String query = "UPDATE Solution SET created = ?, updated = ?, description = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, this.created);
            preparedStatement.setString(2, this.updated);
            preparedStatement.setString(3, this.description);
            preparedStatement.setInt(4, this.id);
            preparedStatement.executeUpdate();
        }
    }

    static public Solution loadSolutionById(Connection conn, int id) throws SQLException {
        String query = "SELECT * FROM Solution WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = rs.getInt("id");
            loadedSolution.created = rs.getString("created");
            loadedSolution.updated = rs.getString("updated");
            loadedSolution.description = rs.getString("description");
            return loadedSolution;
        }
        return null;
    }

    static public Solution[] loadAllSolutions(Connection conn) throws SQLException {
        ArrayList<Solution> solutions = new ArrayList<>();
        String query = "SELECT * FROM Solution";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = rs.getInt("id");
            loadedSolution.created = rs.getString("username");
            loadedSolution.updated = rs.getString("password");
            loadedSolution.description = rs.getString("email");
            solutions.add(loadedSolution);
        }
        Solution[] uArray = new Solution[solutions.size()];
        uArray = solutions.toArray(uArray);
        return uArray;
    }

    public void delete(Connection conn) throws SQLException {
        if (this.id != 0) {
            String query = "DELETE FROM Solution WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }

    static public Solution 
}
