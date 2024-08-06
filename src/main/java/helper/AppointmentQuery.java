package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kduongmain.model.Appointment;
import kduongmain.model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;

public class AppointmentQuery {
    /** SQL Query to get all appointments in the database */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT a.appointment_id, a.title, a.description, a.location, a.contact_id, a.type, a.start, a.end, a.customer_id, a.user_id, a.create_Date, a.created_By, a.last_update, a.last_updated_by, " +
                    "c.customer_name AS customer_name, u.user_name AS user_name, con.contact_name AS contact_name " +
                    "FROM appointments a " +
                    "JOIN customers c ON a.customer_id = c.customer_id " +
                    "JOIN users u ON a.user_id = u.user_id " +
                    "JOIN contacts con ON a.contact_id = con.contact_id";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                String contact = rs.getString("Contact_Name");
                String user = rs.getString("User_Name");
                String customer = rs.getString("Customer_Name");


                Appointment appointment = new Appointment(id, title, description, location, type, start, end, customerId, userId, contactId, customer, user, contact);
                appointments.add(appointment);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return appointments;
    }

    /** Method to add appointments to the database. */
    public static void add(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime createDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy,
                           int customerId, int userId, int contactId) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, " +
                "Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setTimestamp(7, Timestamp.valueOf(createDate));
        ps.setString(8, createdBy);
        ps.setTimestamp(9, lastUpdated);
        ps.setString(10, lastUpdatedBy);
        ps.setInt(11, customerId);
        ps.setInt(12, userId);
        ps.setInt(13, contactId);

        // Execute Query
        ps.executeUpdate();
    }

    /** Method to update the appointment database */
    public static void updated(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end,
                               Timestamp lastUpdated, String lastUpdatedBy, int customerId, int userId, int contactId, int id) {
        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
                    "Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setTimestamp(7, lastUpdated);
            ps.setString(8, lastUpdatedBy);
            ps.setInt(9, customerId);
            ps.setInt(10, userId);
            ps.setInt(11, contactId);
            ps.setInt(12, id);

            // Execute query
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Method to delete appointment based on appointment Id */
    public static void delete(int id) {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, id);

            // Execute Query
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
}
    /** Method to get appointments sorted by week. */
    public static ObservableList<Appointment> getWeeklyAppointments() {
        ObservableList<Appointment> weeklyAppointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE YEARWEEK(START) = YEARWEEK(NOW()) ORDER BY appointments.Appointment_ID";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                Appointment weeklyAppointment = new Appointment(id, title, description, location, type, start, end, customerId, userId, contactId, contactName);
                weeklyAppointments.add(weeklyAppointment);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return weeklyAppointments;
    }

    /** Method to get appointments sorted by monthly */
    public static ObservableList<Appointment> getMonthlyAppointments() {
        ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID " +
                    "WHERE MONTH(Start) = MONTH(NOW()) ORDER BY appointments.Appointment_ID";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                Appointment monthlyAppointment = new Appointment(id, title, description, location, type, start, end, customerId, userId, contactId, contactName);
                monthlyAppointments.add(monthlyAppointment);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return monthlyAppointments;
    }

    /** Method to get appointments sorted for a specific user. */
    public static ObservableList<Appointment> getUserAppointments(int userID) {
        ObservableList<Appointment> userAppointments = FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * FROM Appointments WHERE User_ID  = '" + userID + "'";
            PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment userAppointment = new Appointment(id, title, description, location, type, start, end, customerId, userId, contactId);
                userAppointments.add(userAppointment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userAppointments;
    }

    /** Method to get total number of appointments by month. */
    public static ObservableList<Appointment> getAppointmentMonthlyTotal() {
        ObservableList<Appointment> appointmentMonthlyTotal = FXCollections.observableArrayList();
        try {
            String sql = "SELECT DISTINCT(MONTHNAME(Start)) AS Month, Count(*) AS NUM FROM appointments " +
                    "GROUP BY Month";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("Month");
                int total = rs.getInt("NUM");
                Appointment result = new Appointment(type, total);
                appointmentMonthlyTotal.add(result);
            }
        } catch (SQLException e) {
            System.out.println("ERROR GETTING TOTAL NUMBER OF APPOINTMENTS: " + e.getMessage());
        }
        return appointmentMonthlyTotal;
    }

    /** Method to get a total number of appointments by type. */
    public static ObservableList<Appointment> getNumAppointmentsByType() {
        ObservableList<Appointment> numAppointmentsByType = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Type, Count(*) AS NUM FROM appointments GROUP BY Type";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("Type");
                int total = rs.getInt("NUM");
                Appointment appointment = new Appointment(type, total);
                numAppointmentsByType.add(appointment);
            }
        } catch (SQLException e) {
            System.out.println("ERROR GETTING TOTAL NUMBER OF APPOINTMENTS BY TYPE: " + e.getMessage());
        }
        return numAppointmentsByType;
    }

    /** Method to get appointments for a specific contact by contactId. */
    public static ObservableList<Appointment> getContactAppointments(int contactID) {
        ObservableList<Appointment> contactAppointment = FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * FROM appointments JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID " +
                    "WHERE appointments.Contact_ID  = '" + contactID + "'";
            PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                Appointment appointment = new Appointment(id, title, description, location, type, start, end, customerId, userId, contactId, contactName);
                contactAppointment.add(appointment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contactAppointment;
    }


}
