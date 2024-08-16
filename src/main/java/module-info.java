module kduong.c195software {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens kduongmain to javafx.fxml;
    opens kduongmain.model to javafx.fxml;
    exports kduongmain.model;
    exports kduongmain;
    exports kduongmain.controller;
    opens kduongmain.controller to javafx.fxml;
}