module kduong.c195software {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens kduongmain to javafx.fxml;
    exports kduongmain;
    exports kduongmain.controller;
    opens kduongmain.controller to javafx.fxml;
}