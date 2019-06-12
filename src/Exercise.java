package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Exercise {

    private int id;
    private String title;
    private String description;

    public Exercise() {};

    public Exercise(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public void saveToDB(Connection conn) throws SQLException {
        if (this.id == 0) {
            String query = "INSERT INTO Exercise(title, description) VALUES(?, ?)";
            PreparedStatement preparedStatement
                    = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, this.title);
            preparedStatement.setString(2, this.description);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        } else {
            String query = "UPDATE Exercise SET title = ?, description = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, this.title);
            preparedStatement.setString(2, this.description);
            preparedStatement.setInt(3, this.id);
            preparedStatement.executeUpdate();
        }
    }

    static public Exercise loadExerciseById(Connection conn, int id) throws SQLException {
        String query = "SELECT * FROM Exercise WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            Exercise loadedExercise = new Exercise();
            loadedExercise.id = rs.getInt("id");
            loadedExercise.title = rs.getString("title");
            loadedExercise.description = rs.getString("description");
            return loadedExercise;
        }
        return null;
    }

    static public Exercise[] loadAllExercises(Connection conn) throws SQLException {
        ArrayList<Exercise> exercises = new ArrayList<>();
        String query = "SELECT * FROM Exercise";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Exercise loadedExercise = new Exercise();
            loadedExercise.id = rs.getInt("id");
            loadedExercise.title = rs.getString("title");
            loadedExercise.description = rs.getString("description");
            exercises.add(loadedExercise);
        }
        Exercise[] uExercise = new Exercise[exercises.size()];
        uExercise = exercises.toArray(uExercise);
        return uExercise;
    }

    public void delete(Connection conn) throws SQLException {
        if (this.id != 0) {
            String query = "DELETE FROM Exercise WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }

}
