package org.example.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import org.apache.commons.validator.routines.EmailValidator;
import org.example.models.User;

import java.time.LocalDate;

public class Utility {
    public static final int APP_WIDTH = 1280;
    public static final int APP_HEIGHT = 780;
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

    public static User responseToUserMapper(JsonObject userResponse){
        User user = new User();
        user.setId(userResponse.get("id").getAsLong());
        user.setName(userResponse.get("name").getAsString());
        user.setEmail(userResponse.get("email").getAsString());
        user.setPassword(userResponse.get("password").getAsString());
        LocalDate createdAt = new Gson().fromJson(userResponse.get("created_at"), LocalDate.class);
        user.setCreatedAt(createdAt);

        return user;
    }

    public static String convertHexColor(ColorPicker colorPicker){
        String color = colorPicker.getValue().toString();
        return color.substring(2, color.length() - 2);
    }
}
