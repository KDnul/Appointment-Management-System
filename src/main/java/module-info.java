module kduong.c195software {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens kduong.c195software to javafx.fxml;
    exports kduong.c195software;
    exports kduong.c195software.controller;
    opens kduong.c195software.controller to javafx.fxml;
}