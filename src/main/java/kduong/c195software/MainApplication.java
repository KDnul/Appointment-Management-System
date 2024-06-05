package kduong.c195software;

import helper.CountryQuery;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setTitle("Scheduler!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();

//        int rowsAffected = CountryQuery.insert("USA");
//        CountryQuery.insert("Russia");
//        CountryQuery.insert("China");
//
//        if(rowsAffected > 0) {
//            System.out.println("Delete Successful");
//        } else {
//            System.out.println("Delete Failed");
//        }

        CountryQuery.select();

        launch();
        JDBC.closeConnection();
    }
}