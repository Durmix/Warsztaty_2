package warsztaty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {

    private int id;
    private String username;
    private String password;
    private String email;

    public User() {};

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.setPassword(password);
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public void saveToDB(Connection conn) throws SQLException {
        if (this.id == 0) {
            String query = "INSERT INTO User(username, email, password) VALUES(?, ?, ?)";
            PreparedStatement preparedStatement
                    = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, this.username);
            preparedStatement.setString(2, this.email);
            preparedStatement.setString(3, this.password);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        } else {
            String query = "UPDATE User SET username = ?, email = ?, password = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, this.username);
            preparedStatement.setString(2, this.email);
            preparedStatement.setString(3, this.password);
            preparedStatement.setInt(4, this.id);
            preparedStatement.executeUpdate();
        }
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    static public User loadUserById(Connection conn, int id) throws SQLException {
        String query = "SELECT * FROM User WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            User loadedUser = new User();
            loadedUser.id = rs.getInt("id");
            loadedUser.username = rs.getString("username");
            loadedUser.password = rs.getString("password");
            loadedUser.email = rs.getString("email");
            return loadedUser;
        }
        return null;
    }

    static public User[] loadAllUsers(Connection conn) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM User";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            User loadedUser = new User();
            loadedUser.id = rs.getInt("id");
            loadedUser.username = rs.getString("username");
            loadedUser.password = rs.getString("password");
            loadedUser.email = rs.getString("email");
            users.add(loadedUser);
        }
        User[] uArray = new User[users.size()];
        uArray = users.toArray(uArray);
        return uArray;
    }

    public void delete(Connection conn) throws SQLException {
        if (this.id != 0) {
            String query = "DELETE FROM User WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }

    static public User[] loadAllByGroupId(Connection conn, int id) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM User";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            User loadedUser = new User();
            loadedUser.id = rs.getInt("id");
            loadedUser.username = rs.getString("username");
            loadedUser.password = rs.getString("password");
            loadedUser.email = rs.getString("email");
            users.add(loadedUser);
        }
        User[] uArray = new User[users.size()];
        uArray = users.toArray(uArray);
        return uArray;
    }

}
