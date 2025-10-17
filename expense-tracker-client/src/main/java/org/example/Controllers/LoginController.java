package org.example.Controllers;

import com.google.gson.JsonObject;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import org.example.utils.ApiUtil;
import org.example.utils.Utility;
import org.example.views.LoginView;
import org.example.views.SignUpView;

public class LoginController {
    LoginView loginView;

    public LoginController(LoginView loginView){
        this.loginView = loginView;
        initialize();
    }

    public void initialize() {
        loginView.getLoginButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                String email = loginView.getUsernameField().getText();
                String password = loginView.getPasswordField().getText();

                login(email, password);

            }
        });

        loginView.getSignupLabel().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                new SignUpView().show();
            }
        });
    }


    public void login(String email, String password) {
        JsonObject loginRequest = new JsonObject();
        loginRequest.addProperty("email", email);
        loginRequest.addProperty("password", password);

        try {
            JsonObject response = ApiUtil.fetchApi("/api/v1/user/login", ApiUtil.RequestMethod.POST, loginRequest);

            int status = response.get("status").getAsInt();

            if (status == 200) {
                System.out.println("✅ Login successful: " + response);
                Utility.showAlertDialog(Alert.AlertType.INFORMATION, "Login successful");
            } else {
                System.out.println("❌ Login failed: " + response);
                Utility.showAlertDialog(Alert.AlertType.ERROR, "Failed to authenticate Due to:\n " + response.get("error"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("⚠️ Network or connection error: " + e.getMessage());
        }
    }
}