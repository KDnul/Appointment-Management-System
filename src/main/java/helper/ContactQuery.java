package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kduongmain.model.Contact;
import kduongmain.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactQuery {
    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();


    public static ObservableList<Contact> getAllContacts() {
        try {
            String sql = "SELECT * FROM CONTACTS";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int contactId = rs.getInt("Contact_Id");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");

                Contact contact = new Contact(contactId, contactName, contactEmail);
                allContacts.add(contact);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allContacts;
    }

}
