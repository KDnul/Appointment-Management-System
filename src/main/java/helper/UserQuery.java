package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kduongmain.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserQuery{
    private static ObservableList<User> allUsers = FXCollections.observableArrayList();

    /** Method to retrieve al users from the database.
     * @return all users. */
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

    /** Authentication Method to authenticate user login.
     * @param username Object username.
     * @param password Object password.
     * @return Integer. */
    public static int authenticateUser(String username, String password) {
        try {
            String sql =  "SELECT * FROM users WHERE user_name = '" + username + "' AND password = '" + password +"'";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String userTest = rs.getString("User_Name");
            String passTest = rs.getString("Password");
            System.out.println(userTest);
            System.out.println(passTest);
            if(rs.getString("User_Name").equals(username) && rs.getString("Password").equals(password)) {
                System.out.println("PASSED USERNAME : " + userTest);
                System.out.println("PASSED PASSWORD : " + passTest);
                System.out.println("PASSED USER ID IS : " + rs.getInt("User_ID"));
                return rs.getInt("User_ID");
            }


        } catch(SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

    /** Method to get user id by username.
     * @param username Object user name.
     * @return Integer for user id.*/
    public static int getUserIdByName(String username) throws SQLException {
        int userId = -1; //Default value

        String sql = "SELECT User_ID FROM USERS WHERE User_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();

        // Check if user ID Exists
        if(rs.next()) {
            userId = rs.getInt("User_ID");
        }

        return userId;
    }

}
