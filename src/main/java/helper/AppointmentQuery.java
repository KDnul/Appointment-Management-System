package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kduongmain.model.Appointment;
import kduongmain.model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AppointmentQuery {
    private static final ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /**
     * Method to put all appointments to a list to retrieve for later use.
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        try {
            String sql = "SELECT * FROM APPOINTMENTS";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");
                LocalDateTime appointmentStartTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appointmentEndTime = rs.getTimestamp("End").toLocalDateTime();
                int appointmentCustomerId = rs.getInt("Customer_ID");
                int appointmentUserId = rs.getInt("User_ID");
                int appointmentContactId = rs.getInt("Contact_ID");
                Appointment appointment = new Appointment(appointmentId, appointmentTitle, appointmentDescription,appointmentLocation, appointmentType, appointmentStartTime, appointmentEndTime, appointmentCustomerId, appointmentUserId, appointmentContactId);
                allAppointments.add(appointment);
            }
        } catch(SQLException e) {
            System.out.println("ERROR QUERYING APPOINTMENTS " + e);
        }
        return allAppointments;
    }

    /** Method to delete appointment based on appointmentId
     @param appointmentId Appointment Id. */
    public static int delete(int appointmentId) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
//        int rowsAffected = ps.executeUpdate();
//        return rowsAffected;
        return ps.executeUpdate();
    }


}
