package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kduongmain.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserQuery extends  User{
    private static ObservableList<User> allUsers = FXCollections.observableArrayList();

    /**
     * User constructor
     *
     * @param id       Object id
     * @param name     Object name
     * @param password Object password.
     */
    public UserQuery(int id, String name, String password) {
        super(id, name, password);
    }

    public static ObservableList<User> getAllUsers() {
        try {
            String sql = "SELECT * FROM USERS";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String userPassword = rs.getString("Password");
                User user = new User(userId, userName, userPassword);
                allUsers.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allUsers;
    }

    /** Authentication Method to authenticate user login. */
    public static int authenticateUser(String username, String password) {
        try {
            String sql = "SELECT * FROM USERS WHERE User_Name = " + username + " AND Password = " + password;
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();



        } catch(SQLException e) {
            System.out.println(e);
        }
    }

}
