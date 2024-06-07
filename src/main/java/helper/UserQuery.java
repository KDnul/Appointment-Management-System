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

}
