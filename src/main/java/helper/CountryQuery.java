package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kduongmain.model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CountryQuery {
    /** Method to select all countries from the database. */
    public static void select() throws SQLException {
        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int countryId = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            System.out.print(countryId + " | ");
            System.out.print(countryName + "\n");
        }
    }

    /** Method to select a country by its id.
     * @param countryId Object country id. */
    public static void select(int countryId) throws SQLException {
        String sql = "SELECT * FROM COUNTRIES WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, countryId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int countryIdPK = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            System.out.print(countryIdPK + " | ");
            System.out.print(countryName + "\n");
        }
    }

    /** Method to get the total number of customers per country.
     * @return Total customers per country. */
    public static ObservableList<Country> totalCountry() {
        ObservableList<Country> country = FXCollections.observableArrayList();
        try {
            String sql = "SELECT countries.Country, COUNT(customers.Customer_ID) AS Total FROM countries " +
                    "INNER JOIN first_level_divisions ON  countries.Country_ID = first_level_divisions.Country_ID " +
                    "INNER JOIN customers ON customers.Division_ID = first_level_divisions.Division_ID GROUP BY countries.Country";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String countryName = rs.getString("Country");
                int customerTotal = rs.getInt("Total");
                Country c = new Country(countryName, customerTotal);
                country.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

}
