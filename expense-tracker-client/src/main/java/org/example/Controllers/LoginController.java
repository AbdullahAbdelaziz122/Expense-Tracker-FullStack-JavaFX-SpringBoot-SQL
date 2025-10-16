package org.example.Controllers;

import com.google.gson.JsonObject;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import org.example.utils.ApiUtil;
import org.example.views.LoginView;

import java.io.IOException;
import java.net.HttpURLConnection;

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

                String email = loginView.getUsernameFiled().getText();
                String password = loginView.getPasswordFiled().getText();

                login(email, password);

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
            } else {
                System.out.println("❌ Login failed: " + response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("⚠️ Network or connection error: " + e.getMessage());
        }
    }
}