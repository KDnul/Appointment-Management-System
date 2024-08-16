package kduongmain;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.*;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setTitle("Schedule Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        JDBC.openConnection();

        LocalDate myLd = LocalDate.now();
        LocalTime myLt = LocalTime.now();

        System.out.println("LOCAL DATE IS : " + myLd);
        System.out.println("LOCAL TIME IS : " + myLt);

        LocalDateTime myLtd = LocalDateTime.of(myLd, myLt);
        ZoneId myZoneId = ZoneId.systemDefault();
        ZonedDateTime myZdt = ZonedDateTime.of(myLtd, myZoneId);

        System.out.println("User Time: " + myZdt);

        ZoneId utcZoneId = ZoneId.of("UTC");
        ZonedDateTime utcZdt = ZonedDateTime.ofInstant(myZdt.toInstant(), utcZoneId);
        System.out.println("User time to UTC: " + utcZdt);

        myZdt = ZonedDateTime.ofInstant(utcZdt.toInstant(), utcZoneId);
        System.out.println("UTC to User Time: " + myZdt);

        launch();
        JDBC.closeConnection();
    }
}