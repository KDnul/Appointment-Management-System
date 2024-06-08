package kduongmain;

import helper.CountryQuery;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setTitle("Schedule Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        // display timezones
//        ZoneId.getAvailableZoneIds().stream().sorted().forEach(System.out::println);
        // Filter timezones
//        ZoneId.getAvailableZoneIds().stream().filter(z->z.contains("America")).sorted().forEach(System.out::println);

        LocalDate myLd = LocalDate.now();
        LocalTime myLt = LocalTime.now();

        System.out.println("LOCAL DATE IS : " + myLd);
        System.out.println("LOCAL TIME IS : " + myLt);

        LocalDateTime myLtd = LocalDateTime.of(myLd, myLt);
        ZoneId myZoneId = ZoneId.systemDefault();
        ZonedDateTime myZdt = ZonedDateTime.of(myLtd, myZoneId);
//        System.out.println(myZdt);

//        System.out.println(myZdt.toLocalDate());
//        System.out.println(myZdt.toLocalTime());
//        System.out.println(myZdt.toLocalDate().toString() + " " + myZdt.toLocalTime().toString());

        System.out.println("User Time: " + myZdt);

        ZoneId utcZoneId = ZoneId.of("UTC");
        ZonedDateTime utcZdt = ZonedDateTime.ofInstant(myZdt.toInstant(), utcZoneId);
        System.out.println("User time to UTC: " + utcZdt);

        myZdt = ZonedDateTime.ofInstant(utcZdt.toInstant(), utcZoneId);
        System.out.println("UTC to User Time: " + myZdt);


//        CountryQuery.select();

        launch();
        JDBC.closeConnection();
    }
}