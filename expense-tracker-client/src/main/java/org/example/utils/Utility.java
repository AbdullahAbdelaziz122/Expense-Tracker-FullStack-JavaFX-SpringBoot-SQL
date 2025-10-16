package org.example.utils;

import javafx.scene.control.Alert;

public class Utility {
    public static final int APP_WIDTH = 1614;
    public static final int APP_HEIGHT = 900;
//    public static final int APP_WIDTH = 1641;
//    public static final int APP_HEIGHT = 900;


    public static void showAlertDialog(Alert.AlertType alertType, String message){
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
