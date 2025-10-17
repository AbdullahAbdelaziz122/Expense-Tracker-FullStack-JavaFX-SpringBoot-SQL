package org.example.utils;

import javafx.scene.control.Alert;
import org.apache.commons.validator.routines.EmailValidator;

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

    public static boolean isValidEmail(String email){
        EmailValidator emailValidator = EmailValidator.getInstance();

        return emailValidator.isValid(email);
    }
}
