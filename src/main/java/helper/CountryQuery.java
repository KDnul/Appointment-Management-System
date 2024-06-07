package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CountryQuery {
    public static int insert(String countryName) throws SQLException {
        String sql = "INSERT INTO COUNTRIES (Country) VALUES (?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, countryName);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int update(int countryId, String countryName) throws SQLException {
        String sql = "UPDATE COUNTRIES SET Country = ? WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, countryName);
        ps.setInt(2, countryId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int delete(int countryId) throws SQLException {
        String sql = "DELETE FROM COUNTRIES WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, countryId);
        return ps.executeUpdate();
    }

    public static void deleteAll() throws SQLException {
        String sql = "DELETE FROM COUNTRIES";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.executeUpdate();
    }

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

}
